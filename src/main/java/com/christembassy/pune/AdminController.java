package com.christembassy.pune;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. Endpoint to get all attendance data for the dashboard with optional filtering
    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceDTO>> getAttendance(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String search) {

        List<Attendance> list = attendanceRepository.findAllByOrderBySubmissionTimeDesc();

        List<AttendanceDTO> dtos = list.stream()
                .map(a -> {
                    String sName = a.getResolvedServiceName();
                    return new AttendanceDTO(
                            a.getId(),
                            a.getUserName() != null ? a.getUserName() : (a.getUser() != null ? a.getUser().getName() : "Unknown"),
                            a.getUserEmail() != null ? a.getUserEmail() : (a.getUser() != null ? (a.getUser().getEmail() != null ? a.getUser().getEmail() : a.getUser().getLoginIdentifier()) : "Unknown"),
                            a.getUser() != null ? a.getUser().getPlatform() : "Unknown",
                            a.getCount(),
                            a.getSubmissionTime(),
                            sName
                    );
                })
                .filter(dto -> {
                    // Date filter (YYYY-MM-DD)
                    if (date != null && !date.trim().isEmpty() && !date.equalsIgnoreCase("all")) {
                        if (!date.equals(dto.getDate())) return false;
                    }
                    // Service filter
                    if (service != null && !service.trim().isEmpty() && !service.equalsIgnoreCase("all")) {
                        if (!dto.getServiceName().toLowerCase().contains(service.toLowerCase())) return false;
                    }
                    // Search filter
                    if (search != null && !search.trim().isEmpty()) {
                        String q = search.toLowerCase();
                        boolean matchName = dto.getUserName() != null && dto.getUserName().toLowerCase().contains(q);
                        boolean matchEmail = dto.getUserEmail() != null && dto.getUserEmail().toLowerCase().contains(q);
                        if (!matchName && !matchEmail) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // 2. Summary stats endpoint for dashboard KPI cards & breakdown
    @GetMapping("/attendance/summary")
    public ResponseEntity<Map<String, Object>> getAttendanceSummary() {
        List<Attendance> list = attendanceRepository.findAllByOrderBySubmissionTimeDesc();

        int totalAttendees = 0;
        int totalSubmissions = list.size();
        Set<String> uniqueUsers = new HashSet<>();

        // Group by Date + Service Name
        Map<String, Map<String, Object>> serviceGroupMap = new LinkedHashMap<>();

        for (Attendance a : list) {
            totalAttendees += a.getCount();
            if (a.getUserEmail() != null) {
                uniqueUsers.add(a.getUserEmail());
            } else if (a.getUser() != null) {
                uniqueUsers.add(a.getUser().getLoginIdentifier() != null ? a.getUser().getLoginIdentifier() : a.getUser().getName());
            }

            String dateStr = a.getSubmissionTime() != null ? a.getSubmissionTime().toLocalDate().toString() : "Unknown Date";
            String sName = a.getResolvedServiceName();
            String key = dateStr + "___" + sName;

            if (!serviceGroupMap.containsKey(key)) {
                Map<String, Object> group = new HashMap<>();
                group.put("date", dateStr);
                group.put("serviceName", sName);
                group.put("totalAttendees", 0);
                group.put("submissions", 0);
                serviceGroupMap.put(key, group);
            }

            Map<String, Object> group = serviceGroupMap.get(key);
            group.put("totalAttendees", (int) group.get("totalAttendees") + a.getCount());
            group.put("submissions", (int) group.get("submissions") + 1);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalAttendees", totalAttendees);
        response.put("totalSubmissions", totalSubmissions);
        response.put("uniqueUsers", uniqueUsers.size());
        response.put("serviceBreakdown", new ArrayList<>(serviceGroupMap.values()));

        return ResponseEntity.ok(response);
    }

    // 3. Endpoint to download formatted Excel report with optional filtering
    @GetMapping("/download-report")
    public ResponseEntity<byte[]> downloadExcel(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String service) throws IOException {

        List<Attendance> list = attendanceRepository.findAllByOrderBySubmissionTimeDesc();

        // Apply filters
        List<Attendance> filtered = list.stream().filter(a -> {
            if (date != null && !date.trim().isEmpty() && !date.equalsIgnoreCase("all")) {
                if (a.getSubmissionTime() == null || !date.equals(a.getSubmissionTime().toLocalDate().toString())) {
                    return false;
                }
            }
            if (service != null && !service.trim().isEmpty() && !service.equalsIgnoreCase("all")) {
                if (!a.getResolvedServiceName().toLowerCase().contains(service.toLowerCase())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());

        try (Workbook workbook = new XSSFWorkbook()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");

            // --- HEADER STYLE ---
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // --- DATA STYLES ---
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle centerStyle = workbook.createCellStyle();
            centerStyle.setAlignment(HorizontalAlignment.CENTER);
            centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // ==========================================
            // SHEET 1: DETAILED ATTENDANCE
            // ==========================================
            Sheet sheet1 = workbook.createSheet("Detailed Attendance");
            sheet1.setDisplayGridlines(true);

            String[] columns = {"S.No", "Date", "Service Name", "Member Name", "Email / Phone", "Attendees Count", "Platform", "Submission Time"};
            Row headerRow = sheet1.createRow(0);
            headerRow.setHeightInPoints(24);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            int totalAttendeesCount = 0;

            for (Attendance a : filtered) {
                Row row = sheet1.createRow(rowNum++);
                row.setHeightInPoints(18);

                // S.No
                Cell c0 = row.createCell(0);
                c0.setCellValue(rowNum - 1);
                c0.setCellStyle(centerStyle);

                // Date
                Cell c1 = row.createCell(1);
                c1.setCellValue(a.getSubmissionTime() != null ? a.getSubmissionTime().toLocalDate().toString() : "");
                c1.setCellStyle(centerStyle);

                // Service Name
                Cell c2 = row.createCell(2);
                c2.setCellValue(a.getResolvedServiceName());
                c2.setCellStyle(dataStyle);

                // Member Name
                Cell c3 = row.createCell(3);
                c3.setCellValue(a.getUserName() != null ? a.getUserName() : (a.getUser() != null ? a.getUser().getName() : "Unknown"));
                c3.setCellStyle(dataStyle);

                // Email / Phone
                Cell c4 = row.createCell(4);
                c4.setCellValue(a.getUserEmail() != null ? a.getUserEmail() : (a.getUser() != null ? (a.getUser().getEmail() != null ? a.getUser().getEmail() : a.getUser().getLoginIdentifier()) : "Unknown"));
                c4.setCellStyle(dataStyle);

                // Count
                Cell c5 = row.createCell(5);
                c5.setCellValue(a.getCount());
                c5.setCellStyle(centerStyle);
                totalAttendeesCount += a.getCount();

                // Platform
                Cell c6 = row.createCell(6);
                c6.setCellValue(a.getUser() != null && a.getUser().getPlatform() != null ? a.getUser().getPlatform() : "Web");
                c6.setCellStyle(centerStyle);

                // Submission Time
                Cell c7 = row.createCell(7);
                c7.setCellValue(a.getSubmissionTime() != null ? a.getSubmissionTime().format(dtf) : "");
                c7.setCellStyle(centerStyle);
            }

            // Total Row
            Row totalRow = sheet1.createRow(rowNum);
            totalRow.setHeightInPoints(20);
            Cell totalLabel = totalRow.createCell(4);
            totalLabel.setCellValue("TOTAL ATTENDEES:");
            CellStyle boldRight = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            boldRight.setFont(boldFont);
            boldRight.setAlignment(HorizontalAlignment.RIGHT);
            totalLabel.setCellStyle(boldRight);

            Cell totalVal = totalRow.createCell(5);
            totalVal.setCellValue(totalAttendeesCount);
            CellStyle boldCenter = workbook.createCellStyle();
            boldCenter.setFont(boldFont);
            boldCenter.setAlignment(HorizontalAlignment.CENTER);
            totalVal.setCellStyle(boldCenter);

            for (int i = 0; i < columns.length; i++) {
                sheet1.autoSizeColumn(i);
            }

            // ==========================================
            // SHEET 2: SERVICE SUMMARY BY DATE
            // ==========================================
            Sheet sheet2 = workbook.createSheet("Service Summary");
            sheet2.setDisplayGridlines(true);

            String[] sumColumns = {"Date", "Service Name", "Total Attendees", "Total Device Submissions"};
            Row sumHeaderRow = sheet2.createRow(0);
            sumHeaderRow.setHeightInPoints(24);

            for (int i = 0; i < sumColumns.length; i++) {
                Cell cell = sumHeaderRow.createCell(i);
                cell.setCellValue(sumColumns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Aggregate filtered records by date + service
            Map<String, int[]> summaryMap = new LinkedHashMap<>();
            for (Attendance a : filtered) {
                String dateStr = a.getSubmissionTime() != null ? a.getSubmissionTime().toLocalDate().toString() : "Unknown";
                String sName = a.getResolvedServiceName();
                String key = dateStr + "___" + sName;
                summaryMap.putIfAbsent(key, new int[]{0, 0});
                summaryMap.get(key)[0] += a.getCount();
                summaryMap.get(key)[1] += 1;
            }

            int sumRowNum = 1;
            for (Map.Entry<String, int[]> entry : summaryMap.entrySet()) {
                String[] parts = entry.getKey().split("___");
                Row r = sheet2.createRow(sumRowNum++);
                r.setHeightInPoints(18);

                Cell sc0 = r.createCell(0);
                sc0.setCellValue(parts[0]);
                sc0.setCellStyle(centerStyle);

                Cell sc1 = r.createCell(1);
                sc1.setCellValue(parts.length > 1 ? parts[1] : "");
                sc1.setCellStyle(dataStyle);

                Cell sc2 = r.createCell(2);
                sc2.setCellValue(entry.getValue()[0]);
                sc2.setCellStyle(centerStyle);

                Cell sc3 = r.createCell(3);
                sc3.setCellValue(entry.getValue()[1]);
                sc3.setCellStyle(centerStyle);
            }

            for (int i = 0; i < sumColumns.length; i++) {
                sheet2.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            String filename = "Attendance_Report_" + LocalDate.now().toString() + ".xlsx";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(out.toByteArray());
        }
    }
}

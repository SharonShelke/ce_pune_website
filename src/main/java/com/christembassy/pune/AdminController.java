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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private UserRepository userRepository;
    // 1. Endpoint to get all attendance data for the dashboard
    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance() {
        // Fetch all attendance records
        List<Attendance> list = attendanceRepository.findAll();
        
        // Convert to DTOs
        List<AttendanceDTO> dtos = list.stream().map(a -> new AttendanceDTO(
                a.getUserName() != null ? a.getUserName() : (a.getUser() != null ? a.getUser().getName() : "Unknown"),
                a.getUserEmail() != null ? a.getUserEmail() : (a.getUser() != null ? (a.getUser().getEmail() != null ? a.getUser().getEmail() : a.getUser().getLoginIdentifier()) : "Unknown"),
                a.getUser() != null ? a.getUser().getPlatform() : "Unknown",
                a.getCount(),
                a.getSubmissionTime()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    // 2. Endpoint to download Excel report
    @GetMapping("/download-report")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
        List<Attendance> list = attendanceRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Attendance Report");
            // Header Row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Name", "Email", "Platform", "Count", "Submission Time"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }
            // Data Rows
            int rowNum = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            for (Attendance a : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(a.getUserName() != null ? a.getUserName() : (a.getUser() != null ? a.getUser().getName() : "Unknown"));
                row.createCell(1).setCellValue(a.getUserEmail() != null ? a.getUserEmail() : (a.getUser() != null ? (a.getUser().getEmail() != null ? a.getUser().getEmail() : a.getUser().getLoginIdentifier()) : "Unknown"));
                row.createCell(2).setCellValue(a.getUser() != null ? a.getUser().getPlatform() : "Unknown");
                row.createCell(3).setCellValue(a.getCount());
                row.createCell(4).setCellValue(a.getSubmissionTime() != null ? a.getSubmissionTime().format(formatter) : "");
            }
            // Resize columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // Write to ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attendance_report.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(out.toByteArray());
        }
    }
}

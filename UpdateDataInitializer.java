import java.nio.file.*;
import java.util.*;

public class UpdateDataInitializer {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src/main/java/com/christembassy/pune/DataInitializer.java");
        List<String> lines = Files.readAllLines(path);
        List<String> newLines = new ArrayList<>();
        
        boolean inSeed = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            
            if (line.contains("private void seedFellowships() {")) {
                newLines.add(line);
                newLines.add("        List<Fellowship> fellowships = Arrays.asList(");
                inSeed = true;
                continue;
            }
            
            if (inSeed && line.contains("fellowshipRepository.deleteAll();")) {
                continue;
            }
            
            if (inSeed && line.contains("List<Fellowship> fellowships = Arrays.asList(")) {
                continue;
            }
            
            if (inSeed && line.contains("fellowshipRepository.saveAll(fellowships);")) {
                newLines.add("        List<Fellowship> existing = fellowshipRepository.findAll();");
                newLines.add("        if (existing.isEmpty()) {");
                newLines.add("            fellowshipRepository.saveAll(fellowships);");
                newLines.add("        } else {");
                newLines.add("            for (Fellowship h : fellowships) {");
                newLines.add("                boolean found = false;");
                newLines.add("                for (Fellowship e : existing) {");
                newLines.add("                    if (h.getName().equals(e.getName()) && (h.getLocation().equals(e.getLocation()) || (h.getLocation().isEmpty() && e.getLocation().isEmpty()))) {");
                newLines.add("                        e.setState(h.getState());");
                newLines.add("                        e.setCity(h.getCity());");
                newLines.add("                        found = true;");
                newLines.add("                        break;");
                newLines.add("                    }");
                newLines.add("                }");
                newLines.add("                if (!found) {");
                newLines.add("                    existing.add(h);");
                newLines.add("                }");
                newLines.add("            }");
                newLines.add("            fellowshipRepository.saveAll(existing);");
                newLines.add("        }");
                inSeed = false;
                continue;
            }
            
            newLines.add(line);
        }
        Files.write(path, newLines);
    }
}

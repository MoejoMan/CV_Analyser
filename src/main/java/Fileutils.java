import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class Fileutils {

    public static String readFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            System.out.println("Invalid file path.");
            return null;
        }

        String fileExtension = getFileExtension(filePath);

        try {
            return switch (fileExtension) {
                case "pdf" -> extractTextFromPDF(filePath);
                case "docx" -> extractTextFromDOCX(filePath);
                case "txt" -> extractTextFromTXT(filePath);
                default -> {
                    System.out.println("Unsupported file type: " + fileExtension);
                    yield null;
                }
            };
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    private static String extractTextFromPDF(String pdfPath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            return new PDFTextStripper().getText(document);
        }
    }

    private static String extractTextFromDOCX(String docxPath) throws IOException {
        try (FileInputStream fis = new FileInputStream(docxPath);
             XWPFDocument document = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            return extractor.getText();
        }
    }

    private static String extractTextFromTXT(String txtPath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(txtPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString().trim();
    }

    private static String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        return (lastDotIndex != -1) ? filePath.substring(lastDotIndex + 1).toLowerCase() : "";
    }
}
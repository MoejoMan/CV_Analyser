import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class Fileutils {

    // Extract text from a PDF file
    public static String extractTextFromPDF(String pdfPath) throws IOException {
        PDDocument document = PDDocument.load(new File(pdfPath));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    // Extract text from a DOCX file
    public static String extractTextFromDOCX(String docxPath) throws IOException {
        FileInputStream fis = new FileInputStream(docxPath);
        XWPFDocument document = new XWPFDocument(fis);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        String text = extractor.getText();
        fis.close();
        return text;
    }

    // Extract text from a TXT file
    public static String extractTextFromTXT(String txtPath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(txtPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // Main method to extract based on file type
    public static String readFile(String filePath) {
        String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
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
}

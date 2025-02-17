import java.io.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\jhg56\\Documents\\resume.txt";
        String resumeText = readFile(filePath);

        if (resumeText != null) {
            System.out.println("Resume Contents:\n" + resumeText);
        } else {
            System.out.println("Failed to read the resume.");
        }
    }

    public static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
        return content.toString();
    }
}

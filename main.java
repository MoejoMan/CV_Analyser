import java.io.*;
import java.util.regex.*;

public class Analyser {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\jhg56\\Documents\\resume.txt";
        String resumeText = readFile(filePath);

        if (resumeText != null) {
            displayInfo(resumeText);
        }
    }
}

public static String readFile(String filePath) {
    StringBuilder content = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            content.append(line).append("\n"); // Read each line and add it to content
        }
    } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
        return null;  // Return null if there's an error
    }
    return content.toString(); // Return file contents as a string
}

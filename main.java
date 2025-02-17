import java.io.*;
import java.util.regex.*;

public class Analyser {
    public static void main(String[] args) {
        String filePath = "resume.txt";  // Change this to your file path
        String resumeText = readFile(filePath);

        if (resumeText != null) {
            displayInfo(resumeText);
        }
    }
}
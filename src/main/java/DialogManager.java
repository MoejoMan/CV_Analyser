import java.util.Collections;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DialogManager {

    private final Scanner scanner;

    public DialogManager() {
        scanner = new Scanner(System.in);
    }

    private static final String[] DEFAULT_RESUME = {
            "C:\\Users\\jhg56\\Documents\\resumeWORD.docx",
            "C:\\Users\\jhg56\\Documents\\bad_resume.txt",
            "C:\\Users\\jhg56\\Documents\\good_resume.txt"
    };

    private static final String[] DEFAULT_JOB_DESCRIPTIONS = {
            "C:\\Users\\jhg56\\Documents\\job_descriptionPDF.pdf",
            "C:\\Users\\jhg56\\Documents\\job_description2.txt",
            "C:\\Users\\jhg56\\Documents\\job_description3.txt"
    };

    public List<String> getResumes() {
        List<String> resumes = new ArrayList<>();
        System.out.println("Enter resume file paths (Press Enter to stop, if none are entered the program will use defaults):");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                break;
            }
            resumes.add(input);
        }

        if (resumes.isEmpty()) {
            Collections.addAll(resumes, DEFAULT_RESUME);
        }

        return resumes;
    }

    public String[] getJobDescriptions() {
        System.out.println("Do you want to manually enter a job description or use file paths? (manual/paths)");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("manual")) {
            System.out.println("Please enter the job description:");
            String manualDescription = scanner.nextLine();
            return new String[]{manualDescription};
        } else {
            System.out.println("Please enter the file paths of the job descriptions, separated by commas (Press Enter to use defaults):");
            String input = scanner.nextLine().trim();
            return input.isEmpty() ? DEFAULT_JOB_DESCRIPTIONS : input.split(",");
        }
    }

    public boolean start() {
        System.out.println("Start analysis? (yes/no)");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes");
    }
}

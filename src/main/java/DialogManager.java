import java.util.Scanner;

public class DialogManager {

    private Scanner scanner;

    public DialogManager() {
        scanner = new Scanner(System.in);
    }

    // Default file paths
    private static final String DEFAULT_RESUME_PATH = "C:\\Users\\jhg56\\Documents\\resumeWORD.docx";
    private static final String[] DEFAULT_JOB_DESCRIPTION_PATHS = {
            "C:\\Users\\jhg56\\Documents\\job_descriptionPDF.pdf",
            "C:\\Users\\jhg56\\Documents\\job_description2.txt",
            "C:\\Users\\jhg56\\Documents\\job_description3.txt"
    };

    // Ask the user for the resume file path
    public String askResumeFilePath() {
        System.out.println("Please enter the file path of the resume (Press Enter to use default):");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? DEFAULT_RESUME_PATH : input;
    }

    // Ask the user for job description file paths
    public String[] askJobDescriptionFilePaths() {
        System.out.println("Please enter the file paths of the job descriptions, separated by commas (Press Enter to use defaults):");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? DEFAULT_JOB_DESCRIPTION_PATHS : input.split(",");
    }

    // Ask if the user wants to start the analysis
    public boolean confirmStartAnalysis() {
        System.out.println("Do you want to start the analysis? (yes/no)");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes");
    }
}

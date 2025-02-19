import java.util.Scanner;

public class DialogManager {

    private final Scanner scanner;

    public DialogManager() {
        scanner = new Scanner(System.in);
    }

    // Default file paths
    private static final String DEFAULT_RESUME = "C:\\Users\\jhg56\\Documents\\resumeWORD.docx";
    private static final String[] DEFAULT_JOB_DESCRIPTIONS = {
            "C:\\Users\\jhg56\\Documents\\job_descriptionPDF.pdf",
            "C:\\Users\\jhg56\\Documents\\job_description2.txt",
            "C:\\Users\\jhg56\\Documents\\job_description3.txt"
    };

    // Get the resume file path
    public String getResume() {
        System.out.println("Enter the resume file path (Press Enter to use default):");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? DEFAULT_RESUME : input;
    }

    // Get job description file paths
    public String[] getJobDescriptions() {
        System.out.println("Do you want to manually enter a job description or use file paths? (manual/paths)");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("manual")) {
            System.out.println("Please enter the job description:");
            String manualDescription = scanner.nextLine();
            return new String[]{manualDescription};
        } else {
            // Use existing file path logic (default or custom)
            System.out.println("Please enter the file paths of the job descriptions, separated by commas (Press Enter to use defaults):");
            String input = scanner.nextLine().trim();
            return input.isEmpty() ? DEFAULT_JOB_DESCRIPTIONS : input.split(",");
        }
    }

    // Confirm if the user wants to start the analysis
    public boolean start() {
        System.out.println("Start analysis? (yes/no)");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes");
    }
}

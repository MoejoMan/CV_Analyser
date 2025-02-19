import java.util.Scanner;

public class DialogManager {

    private Scanner scanner;

    public DialogManager() {
        scanner = new Scanner(System.in);
    }

    // Ask the user for the resume file path
    public String askResumeFilePath() {
        System.out.println("Please enter the file path of the resume:");
        return scanner.nextLine();
    }

    // Ask the user for job description file paths
    public String[] askJobDescriptionFilePaths() {
        System.out.println("Please enter the file paths of the job descriptions, separated by commas:");
        String input = scanner.nextLine();
        return input.split(",");
    }

    // Ask if the user wants to start the analysis
    public boolean confirmStartAnalysis() {
        System.out.println("Do you want to start the analysis? (yes/no)");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes");
    }
}

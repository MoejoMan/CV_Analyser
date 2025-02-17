import java.io.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        //defined file path for the resume, this is the path of the file in my computer.
        String filePath = "C:\\Users\\jhg56\\Documents\\resume.txt";
        String resumeText = readFile(filePath);

        //if the file is read successfully, print the contents of the file and extract skills.
        if (resumeText != null) {
            System.out.println("Resume Contents:\n" + resumeText);
            extractSkills(resumeText);
            extractEmail(resumeText);
            extractPhone(resumeText);
            extractName(resumeText);
        } else {
            System.out.println("Failed to read the resume.");
        }
    }

    public static void extractSkills(String resumeText) {
        //skills to be searched in the resume
        String[] skills = {"Java", "Python", "SQL", "JavaScript"};

        for (String skill : skills) {
            Pattern pattern = Pattern.compile("\\b" + skill + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(resumeText);
            //if the skill is found in the resume, it is printed, otherwise not found message is printed.
            if (matcher.find()) {
                System.out.println("Found skill: " + skill);
            } else {
                System.out.println("Skill not found: " + skill);
            }
        }
    }

//regex to find an email in the file, it doesn't validate the email, rather simply checks for ones existence within the file.
public static void extractEmail(String resumeText) {
    Pattern emailPattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\\b");
    Matcher emailMatcher = emailPattern.matcher(resumeText);
    if (emailMatcher.find()) {
        System.out.println("Email found: " + emailMatcher.group());
    } else {
        System.out.println("No email found.");
    }
}

    }
//regex tp find a valid UK phone number in the file.
public static void extractPhone(String resumeText) {
    Pattern phonePattern = Pattern.compile("^(((\\+44\\s?\\d{4}|\\(?0\\d{4}\\)?)\\s?\\d{3}\\s?\\d{3})|((\\+44\\s?\\d{3}|\\(?0\\d{3}\\)?)\\s?\\d{3}\\s?\\d{4})|((\\+44\\s?\\d{2}|\\(?0\\d{2}\\)?)\\s?\\d{4}\\s?\\d{4}))(\s?\\#(\d{4}|\d{3}))?$");
    Matcher phoneMatcher = phonePattern.matcher(resumeText);
    if (phoneMatcher.find()) {
        System.out.println("Phone number found: " + phoneMatcher.group());
    } else {
        System.out.println("No phone number found.");
    }
}

//regex to find a name in the file, including uppercase, lowercase, hyphons and accented letters etc...
public static void extractName(String resumeText) {
    Pattern namePattern = Pattern.compile("^[A-Za-zÀ-ÿ]+(?:[-'\\s][A-Za-zÀ-ÿ]+)*$");
    Matcher nameMatcher = namePattern.matcher(resumeText);
    if (nameMatcher.find()) {
        System.out.println("Name found: " + nameMatcher.group());
    } else {
        System.out.println("No name found.");
    }
}

//tis is what reads the file.
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
        // this returns null if the file is not read successfully.
    }
    return content.toString();
    }
}

//test

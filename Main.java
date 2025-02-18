import java.io.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        String resumeFilePath = "C:\\Users\\jhg56\\Documents\\resume.txt";

        // List of job description files
        String[] jobDescriptionFiles = {
                "C:\\Users\\jhg56\\Documents\\job_description.txt",
                "C:\\Users\\jhg56\\Documents\\job_description2.txt",
                "C:\\Users\\jhg56\\Documents\\job_description3.txt"
        };

        String resumeText = readFile(resumeFilePath);

        if (resumeText != null) {
            extractSkills(resumeText, "Resume");
            extractEmail(resumeText);
            extractPhone(resumeText);
            extractName(resumeText);
            extractEducation(resumeText);
            extractWorkExperience(resumeText);
        } else {
            System.out.println("Failed to read the resume.");
        }

        for (String jobDescriptionFile : jobDescriptionFiles) {
            String jobDescriptionText = readFile(jobDescriptionFile);
            if (jobDescriptionText != null) {
                System.out.println("\n--- Processing " + jobDescriptionFile + " ---");
                extractSkills(jobDescriptionText, "Job Description");
            } else {
                System.out.println("Failed to read: " + jobDescriptionFile);
            }
        }
    }

    public static void extractSkills(String text, String sourceType) {
        String[] skills = {"Java", "Python", "C++", "SQL", "JavaScript", "PHP", "HTML", "Git", "AWS", "Docker"};

        System.out.println("Extracting skills from " + sourceType + ":");

        for (String skill : skills) {
            Pattern pattern = Pattern.compile("\\b" + skill + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                System.out.println("Found skill: " + skill);
            } else {
                System.out.println("Skill not found: " + skill);
            }
        }
    }

    public static void extractEmail(String resumeText) {
        Pattern emailPattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\\b");
        Matcher emailMatcher = emailPattern.matcher(resumeText);
        if (emailMatcher.find()) {
            System.out.println("Email found: " + emailMatcher.group());
        } else {
            System.out.println("No email found.");
        }
    }

    public static void extractPhone(String resumeText) {
        Pattern phonePattern = Pattern.compile(
                "^((\\+44)|(0)) ?\\d{4} ?\\d{6}$"
        );
        Matcher phoneMatcher = phonePattern.matcher(resumeText);
        if (phoneMatcher.find()) {
            System.out.println("Phone number found: " + phoneMatcher.group());
        } else {
            System.out.println("No phone number found.");
        }
    }

    public static void extractName(String resumeText) {
        Pattern namePattern = Pattern.compile("\\b[A-Z][a-z]+(?:\\s[A-Z][a-z]+)+\\b");
        Matcher nameMatcher = namePattern.matcher(resumeText);
        if (nameMatcher.find()) {
            System.out.println("Name found: " + nameMatcher.group());
        } else {
            System.out.println("No name found.");
        }
    }

    public static void extractEducation(String resumeText) {
        Pattern educationPattern = Pattern.compile("(?i)(education|academic background|qualifications|degree)(.*?)(?:experience|skills|work|certifications|$)", Pattern.DOTALL);
        Matcher educationMatcher = educationPattern.matcher(resumeText);
        if (educationMatcher.find()) {
            String educationSection = educationMatcher.group(2);
            System.out.println("Education Section: " + educationSection);

            Pattern degreePattern = Pattern.compile("\\b(Bachelor|Master|PhD|Degree)\\b.*?\\b(University|College)\\b", Pattern.CASE_INSENSITIVE);
            Matcher degreeMatcher = degreePattern.matcher(educationSection);
            while (degreeMatcher.find()) {
                System.out.println("Degree: " + degreeMatcher.group());
            }
        } else {
            System.out.println("No Education found.");
        }
    }

    public static void extractWorkExperience(String resumeText) {
        Pattern workExperiencePattern = Pattern.compile("(?i)(experience|work history|professional experience)(.*?)(?:education|skills|certifications|$)", Pattern.DOTALL);
        Matcher workExperienceMatcher = workExperiencePattern.matcher(resumeText);
        if (workExperienceMatcher.find()) {
            String workExperienceSection = workExperienceMatcher.group(2);
            System.out.println("Work Experience Section: " + workExperienceSection);

            Pattern jobTitlePattern = Pattern.compile("(\\b(?:Software|Senior|Junior)?\\s?[A-Za-z]+(?:\\s[A-Za-z]+)?\\s?Developer|Engineer|Manager\\b)", Pattern.CASE_INSENSITIVE);
            Matcher jobTitleMatcher = jobTitlePattern.matcher(workExperienceSection);
            while (jobTitleMatcher.find()) {
                System.out.println("Job Title: " + jobTitleMatcher.group());
            }

            Pattern companyPattern = Pattern.compile(
                    "\\b(?:at|for|with|working for|employed by)\\s+([A-Z][a-zA-Z&.,-]+(?:\\s[A-Z][a-zA-Z&.,-]+)*)\\b",
                    Pattern.CASE_INSENSITIVE
            );
            Matcher companyMatcher = companyPattern.matcher(workExperienceSection);
            while (companyMatcher.find()) {
                System.out.println("Company: " + companyMatcher.group(1));
            }
        } else {
            System.out.println("No Work Experience found.");
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
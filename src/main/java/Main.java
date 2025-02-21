import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

public class Main {
    public static String[] skills = {"Java", "Python", "C++", "C#", "SQL", "JavaScript", "PHP", "HTML", "Git", "AWS", "Docker", "Command Line", "Linux", "Shell", "CMD"};

    public static void main(String[] args) {
        DialogManager fileHandler = new DialogManager();

        // Get multiple resumes
        List<String> resumeFiles = fileHandler.getResumes();

        String[] jobDescriptions = fileHandler.getJobDescriptions();

        if (!fileHandler.start()) {
            System.out.println("Analysis cancelled.");
            return;
        }

        System.out.println("Job Descriptions Loaded:");
        for (String job : jobDescriptions) {
            System.out.println("- " + job);
        }

        for (String resumeFile : resumeFiles) {
            String resumeText = Fileutils.readFile(resumeFile);

            if (resumeText != null) {
                // Process each resume
                System.out.println("\nProcessing Resume: " + resumeFile);
                extractSkills(resumeText, "Resume");
                extractEmail(resumeText);
                extractPhone(resumeText);
                extractName(resumeText);
                extractEducation(resumeText);
                extractWorkExperience(resumeText);

                for (String jobDescription : jobDescriptions) {
                    File jobFile = new File(jobDescription.trim());

                    if (jobFile.exists() && jobFile.isFile()) {
                        String jobDescriptionText = Fileutils.readFile(jobDescription.trim());
                        if (jobDescriptionText != null && !jobDescriptionText.trim().isEmpty()) {
                            System.out.println("\n--- Processing Job Description from file ---");
                            extractSkills(jobDescriptionText, "Job Description");

                            double matchPercentage = RankingsManager.calculateSkillMatch(resumeText, jobDescriptionText, skills);
                            System.out.printf("Skill Match Percentage for Job Description file: %.2f%%\n", matchPercentage);
                        } else {
                            System.out.println("Failed to read or file is empty: " + jobDescription);
                        }
                    } else {
                        System.out.println("\n--- Processing Manual Job Description ---");
                        extractSkills(jobDescription, "Job Description");

                        double matchPercentage = RankingsManager.calculateSkillMatch(resumeText, jobDescription, skills);
                        System.out.printf("Skill Match Percentage for Manual Description: %.2f%%\n", matchPercentage);
                    }
                }
            } else {
                System.out.println("Failed to read the resume: " + resumeFile);
            }
        }
    }

    public static void extractSkills(String text, String sourceType) {
        System.out.println("Extracting skills from " + sourceType + "...");

        for (String skill : skills) {
            if (text.contains(skill)) {
                System.out.println(skill + " found in " + sourceType);
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
        Pattern phonePattern = Pattern.compile("\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{3,4}[-.\\s]?\\d{4,6}");
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
}

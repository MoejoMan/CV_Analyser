import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        // Resume file path
        String resumeFilePath = "C:\\Users\\jhg56\\Documents\\resumeWORD.docx";

        // List of job description file paths
        String[] jobDescriptionFiles = {
                "C:\\Users\\jhg56\\Documents\\job_descriptionPDF.pdf",
                "C:\\Users\\jhg56\\Documents\\job_description2.txt",
                "C:\\Users\\jhg56\\Documents\\job_description3.txt"
        };

        String resumeText = readFile(resumeFilePath);
        // Extract skills, email, phone, name, education, and work experience from the resume
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
        // Extract skills from each job description file
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
    // A list of skills to extract from the text
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

    public static String extractTextFromPDF(String pdfPath) throws IOException {
        PDDocument document = PDDocument.load(new File(pdfPath));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    public static String extractTextFromDOCX(String docxPath) throws IOException {
        FileInputStream fis = new FileInputStream(docxPath);
        XWPFDocument document = new XWPFDocument(fis);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        String text = extractor.getText();
        fis.close();
        return text;
    }

    // Extract email from the text
    public static void extractEmail(String resumeText) {
        Pattern emailPattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\\b");
        Matcher emailMatcher = emailPattern.matcher(resumeText);
        if (emailMatcher.find()) {
            System.out.println("Email found: " + emailMatcher.group());
        } else {
            System.out.println("No email found.");
        }
    }
    // Extract phone number from the text (now working, only for UK numbers)
    public static void extractPhone(String resumeText) {
        Pattern phonePattern = Pattern.compile("\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{3,4}[-.\\s]?\\d{4,6}");
        Matcher phoneMatcher = phonePattern.matcher(resumeText);
        if (phoneMatcher.find()) {
            System.out.println("Phone number found: " + phoneMatcher.group());
        } else {
            System.out.println("No phone number found.");
        }
    }
    // Extract name from the text (can get regex confused on uni name vs actual candidate name)
    public static void extractName(String resumeText) {
        Pattern namePattern = Pattern.compile("\\b[A-Z][a-z]+(?:\\s[A-Z][a-z]+)+\\b");
        Matcher nameMatcher = namePattern.matcher(resumeText);
        if (nameMatcher.find()) {
            System.out.println("Name found: " + nameMatcher.group());
        } else {
            System.out.println("No name found.");
        }
    }
    // Extract education from the text (what type of degree and where from)
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
    // Extract work experience from the text (job title and company)
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
    // Read the content of a file
    public static String readFile(String filePath) {
        String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
        // Extract text based on whether it is a PDF, DOCX, or TXT file
        try {
            return switch (fileExtension) {
                case "pdf" -> extractTextFromPDF(filePath);
                case "docx" -> extractTextFromDOCX(filePath);
                case "txt" ->
                        readTextFile(filePath);
                default -> {
                    System.out.println("Unsupported file type: " + fileExtension);
                    yield null;
                }
            };
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

// Read the content of a text file
    private static String readTextFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}



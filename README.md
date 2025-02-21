This CV analyser is a Java-based tool that allows users to upload resumes and job descriptions in various formats to evaluate the match between the skills mentioned in the resumes and those required in the job descriptions. It calculates a skill match percentage to help determine the suitability of candidates for a job position.

Features
Skill Extraction: Extracts skills from resumes and job descriptions to determine the most relevant skills.
Multiple Resume Support: Allows users to process multiple resumes at once.
Job Description Matching: Supports job description analysis from both file input and manual input.
Skill Match Percentage: Calculates the percentage of skills in the resume that match the skills required by the job description.
Default or Custom File Paths: Users can either enter file paths manually or use the default file paths provided by the program.
Prerequisites
Java 8 or higher is required to run the program.
The following files are needed for the program to work:
Resumes (in .docx, .txt formats)
Job Descriptions (in .txt, .pdf formats)

File Descriptions
Main.java: The main class that drives the CV Analyser program. Handles reading resumes and job descriptions, processing the data, and outputting the skill match percentage.
DialogManager.java: Handles user input for file paths and job description selection. Prompts the user for necessary inputs.
Fileutils.java: Contains helper methods for reading the contents of resume and job description files.
RankingsManager.java: Calculates the skill match percentage between a resume and job description based on common skills.

Although basic in scope, it should be able to extract basic details from a resume matching it to a job description in relatively little time.

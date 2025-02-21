import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RankingsManager {

    public static double calculateSkillMatch(String resumeText, String jobDescriptionText, String[] requiredSkills) {
        int totalSkills = requiredSkills.length;
        int matchedSkills = 0;

        for (String skill : requiredSkills) {
            String skillPattern = "\\b" + skill + "\\b";

            Pattern pattern = Pattern.compile(skillPattern, Pattern.CASE_INSENSITIVE);
            Matcher resumeMatcher = pattern.matcher(resumeText);
            Matcher jobMatcher = pattern.matcher(jobDescriptionText);

            if (resumeMatcher.find() && jobMatcher.find()) {
                matchedSkills++;
                System.out.println("Matched skill: " + skill);
            }
        }

        if (totalSkills == 0) return 0;

        double matchPercentage = ((double) matchedSkills / totalSkills) * 100;
        System.out.printf("Total Skills: %d, Matched Skills: %d, Match Percentage: %.2f%%\n", totalSkills, matchedSkills, matchPercentage);
        return matchPercentage;
    }
}

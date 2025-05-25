package gr.aueb.cs.projects.captcha;

public class CaptchaChecker {
    public static boolean checkCaptcha(String expected, String userInput) {
        return expected.equalsIgnoreCase(userInput.trim());
    }
}
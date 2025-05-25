package gr.aueb.cs.projects.captcha;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String captcha = CaptchaGenerator.generateCaptcha();
        System.out.println("Πληκτρολόγησε το παρακάτω CAPTCHA:");
        System.out.println(">>> " + captcha);

        System.out.print("Εισαγωγή: ");
        String userInput = scanner.nextLine();

        if (CaptchaChecker.checkCaptcha(captcha, userInput)) {
            System.out.println("✅ Επιτυχία!");
        } else {
            System.out.println("❌ Λάθος CAPTCHA.");
        }

        scanner.close();
    }
}
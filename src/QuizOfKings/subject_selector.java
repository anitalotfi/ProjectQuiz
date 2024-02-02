package QuizOfKings;

import java.util.Scanner;

/**
 * The subject_selector class provides a method for users to select a subject option for the quiz.
 *
 * @sethsolves Sara Sharifirad
 * @anitalotfi Anitta Lotfi
 */

public class subject_selector {
    // Variable to store the selected subject.
    protected static String subject;

    /**
     * Allows users to select a subject by entering its option (1/2/3).
     * Options:
     * 1.) Programming
     * 2.) General Knowledge
     * 3.) Science
     *
     * @return the selected subject.
     */
    static String select_subjet() {

        System.out.println("Select Subject by entering its option(1/2/3):-");
        System.out.println("1.) Programming     2.) General Knowledge     3.) Science");

        // Scanner to read input from the console.
        Scanner scan = new Scanner(System.in);
        // Read the selected subject option.
        int s = scan.nextInt();

        // Determine the subject based on the selected option.
        switch (s) {
            case 1:
                subject = "programming";
                break;
            case 2:
                subject = "General_knowledge";
                break;
            case 3:
                subject = "Science";
                break;
            default:
                // Display an error message for an invalid subject option and exit the program.
                System.out.println("Please select valid subject");
                System.exit(1);
        }
        return subject;
    }

}
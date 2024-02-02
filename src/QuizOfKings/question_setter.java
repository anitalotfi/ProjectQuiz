package QuizOfKings;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;

/**
 * The question_setter class facilitates the process of setting up quiz questions and answer keys.
 * It interacts with subject files, generates random questions, and writes them to "Questions.txt" and "AnswerKey.txt".
 *
 * @sethsolves Sara Sharifirad
 * @anitalotfi Anitta Lotfi
 */

public class question_setter {
    // Variable to store the number of questions for the quiz.
    public static int ques = 0;

    /**
     * Sets up questions and answer keys for the quiz.
     *
     * @return true if questions and answer keys are set successfully, false otherwise.
     */
    public static boolean set_questions() {
        // Set to store unique question numbers.
        Set<Integer> que = new HashSet<Integer>();
        // Scanner to read input from the console.
        Scanner s = new Scanner(System.in);

        // Prompt user to enter the number of questions for the quiz.
        System.out.print("Enter number of Questions in ProjectAP.Quiz: ");
        int n = s.nextInt();
        // Set the 'ques' variable to the number of questions.
        ques = n;

        // Display a message indicating the selection of questions based on the subject.
        System.out.println("Selecting qustions of " + subject_selector.subject + " ...");

        try {
            // Sleep for 2000 milliseconds to simulate a delay.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.print(e);
        }

        // Variable to generate random numbers.
        int num = 0;
        try {
            // FileWriter objects to write questions and answer keys to files.
            FileWriter fw1 = new FileWriter("ProjectAP/Questions.txt");
            FileWriter fw2 = new FileWriter("ProjectAP/AnswerKey.txt");

            // Loop to select and write questions and answer keys.
            while (n-- > 0) {
                boolean flag = false;

                // Loop to generate a unique random number within certain conditions.
                while (!flag) {
                    num = (int) (Math.random() * 100);

                    if (num <= 30 && num % 2 == 1) {
                        if (que.contains(num)) {
                            flag = false;
                        } else {
                            que.add(num);
                            flag = true;
                        }
                    }
                }
                num--;

                // Loop to read lines from the subject file and write them to "Questions.txt".
                for (int i = 0; i < 2; i++) {
                    String line1;
                    try (Stream<String> lines = Files.lines(Paths.get(subject_selector.subject + ".txt"))) {
                        line1 = lines.skip(num).findFirst().get();

                        fw1.write(line1 + '\n');
                    }
                    num++;
                }
                num--;

                // Read a line from the subject solution file and write it to "AnswerKey.txt".
                String line2;
                try (Stream<String> lines = Files.lines(Paths.get(subject_selector.subject + "_sol" + ".txt"))) {
                    line2 = lines.skip((int) (num / 2)).findFirst().get();

                    fw2.write(line2 + '\n');
                }

                catch (IOException e) {
                    System.out.println(e);
                }

                num += 2;
            }

            // Close the FileWriter objects.
            fw1.close();
            fw2.close();
        }

        catch (IOException e) {
            System.out.println(e);
        }
        // Return true after successfully setting questions and answer keys.
        return true;
    }
}

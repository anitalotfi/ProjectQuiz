package QuizOfKings;

import java.util.Scanner;

/**
 * The Initiator class represents the entity responsible for starting the quiz.
 * It prompts the user to enter 'START' to initiate the quiz countdown, allowing the quiz to begin.
 *
 * @version 1.0
 * @since 2024-02-01
 * @sethsolves Sara Sharifirad
 * @anitalotfi Anitta Lotfi
 */

public class Initiator extends Thread {

    /**
     * Initiates the quiz by prompting the user to enter 'START'.
     * If 'START' is entered, it initiates a countdown before returning true.
     * If 'QUIT' is entered, it exits the quiz and returns false.
     *
     * @return true if the quiz is started successfully, false otherwise.
     */
    public static boolean initiator() {

        boolean flag = false;
        int countdown = 5;
        Scanner scan = new Scanner(System.in);
        while (flag == false) {

            System.out.println("!! Enter 'START' to begin the ProjectAP.Quiz !!");

            String input = scan.nextLine();

            if (input.equals("START")) {
                System.out.print("ProjectAP.Quiz starting in " + countdown);
                while (countdown-- > 0) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                    }
                    System.out.print("\b" + countdown);
                }
                System.out.println("");
                flag = true;
                return flag;
            } else {
                System.out.println(
                        "Please enter 'START' correctly. If you not want to start quiz then just enter 'QUIT'");
                String in = scan.next();
                if (in.equals("QUIT")) {
                    System.out.print("EXITING the ProjectAP.Quiz");
                    int i = 2;
                    while (i-- > 0) {
                        for (int j = 0; j < 3; j++) {

                            System.out.print(".");
                            try {
                                Thread.sleep(700);
                            } catch (InterruptedException e) {
                            }
                            if (j == 2) {
                                System.out.print("\b\b\b");
                            }
                        }
                    }
                    return false;
                }
                if (in.equals("START")) {
                    input = "START";
                    System.out.print("ProjectAP.Quiz starting in " + countdown);
                    while (countdown-- > 0) {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                        }
                        System.out.print("\b" + countdown);
                    }
                    System.out.println("");
                    return true;

                }
            }
        }
        return true;
    }
}
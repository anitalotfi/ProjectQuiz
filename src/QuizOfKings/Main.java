package QuizOfKings;

import java.util.Scanner;

/**
 * The Main class serves as the entry point for the quiz application.
 * It extends the Host class, initializing and coordinating the quiz setup, including subject selection,
 * question configuration, and participant connection.
 *
 * @sethsolves Sara Sharifirad
 * @anitalotfi Anitta Lotfi
 */

public class Main extends Host {

    /**
     * The main method that orchestrates the quiz setup and participant connection.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        // Creating a Host object to manage quiz setup.
        Host Host_obj = new Main();

        // Ensuring synchronization with the Host object.
        synchronized (Host_obj) {

            // Select subject for the quiz.
            Host_obj.subject();

            // Select questions for the quiz.
            boolean Questions = Host_obj.questions();
            if (Questions) {
                System.out.println("Successfully selected " + question_setter.ques + " questions for the ProjectAP.Quiz..");
            } else {
                System.out.println("!! Unable to select questions !!");
            }

            // Select time for each question.
            System.out.print("Enter time(in seconds) for each question : ");
            int time;
            Scanner scan = new Scanner(System.in);

            time = scan.nextInt();
            Host_obj.time(time);

            System.out.println("Waiting for participants to connect..");

            // Creating ClientThreads and connecting to the server.
            ProjectAP.ClientThread.initialise(question_setter.ques, Set_time.t);
            ProjectAP.ClientThread clientThread = new ProjectAP.ClientThread(1000);
            ProjectAP.ClientThread clientThread1 = new ProjectAP.ClientThread(1001);
            ProjectAP.ClientThread clientThread2 = new ProjectAP.ClientThread(1002);

            // Starting the participant threads.
            clientThread.start();
            clientThread1.start();
            clientThread2.start();
        }
    }
}

package QuizOfKings;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * The Quiz class represents a quiz session, managing the flow of questions and answers between participants
 * and the quiz server. It includes methods to send questions to participants, receive answers, and handle synchronization.
 *
 * @author sethsolves: Sara Sharifirad
 *         anitalotfi: Anitta Lotfi
 */

class Quiz {
    reader reader = new reader();
    /**
     * The number of the current question in the quiz session.
     */
    int quesNum = 0;

    /**
     * The number of threads currently active in the quiz session.
     */
    int threadnum = 0;

    /**
     * The static variables storing the current question, options, and correct answer.
     */
    static String Question, Option, Answer;

    /**
     * A flag indicating whether the quiz has started.
     */
    static boolean has_started = false;

    /**
     * Sends a question to a participant using the provided DataOutputStream.
     *
     * @param dataOutputStream The DataOutputStream for sending data to the participant.
     * @param count            The count of participants.
     * @throws IOException            If an I/O error occurs.
     * @throws InterruptedException   If the thread is interrupted while waiting.
     */
    void sendQues(DataOutputStream dataOutputStream, int count) throws IOException, InterruptedException {
        // Increments 'threadNum' and waits if 'threadNum' is less than 3, ensuring synchronization.
        threadnum++;
        if (threadnum < 3) {
            synchronized (this) {
                wait();
            }
        }

        // Checks if the quiz has started. If not, it calls a static method 'initiator()' from the 'Initiator' class.
        if (!has_started) {
            if (Initiator.initiator()) { // call initiator if everything is fine
                System.out.println("!! ProjectAP.Quiz is LIVE now !!");
                System.out.println("-------------------------");
            }
            has_started = true;
        }

        // Writes the question and options to the participant's DataOutputStream.
        dataOutputStream.writeUTF(Question);
        dataOutputStream.writeUTF(Option);

        // If three threads have sent questions, it synchronizes and reads the answer using an undefined 'reader' and
        // notifies waiting threads.
        if (threadnum == 3)
            synchronized (this) {
                Answer = reader.readAns();
                threadnum = 0;
                notifyAll();
            }

        // Pauses the thread for 5 seconds.
        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * Receives an answer from a participant using the provided DataInputStream and checks if it matches the correct answer.
     *
     * @param dataInputStream The DataInputStream for receiving data from the participant.
     * @return 1 if the received answer matches the correct answer, else returns 0.
     * @throws IOException If an I/O error occurs.
     */
    int receiveAns(DataInputStream dataInputStream) throws IOException {
        String ans = dataInputStream.readUTF();
        if (ans.equals(Answer))
            return 1;
        return 0;
    }
}

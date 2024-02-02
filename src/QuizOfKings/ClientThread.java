package ProjectAP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * The Quiz class represents a quiz session, managing the flow of questions and answers between participants
 * and the quiz server. It includes methods to send questions to participants, receive answers, and handle synchronization.
 *
 * @author sethsolves: Sara Sharifirad
 *         anitalotfi: Anitta Lotfi
 */

class Quiz {
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

/**
 * The ClientThread class represents a thread handling communication with a participant in the quiz.
 * It extends the Thread class and manages the quiz flow, including sending questions, receiving answers,
 * and updating participant scores.
 *
 * Usage:
 * 1. Create an instance of ClientThread for each participant, providing the participant's port number.
 * 2. Initialize the quiz parameters using the static method initialise before starting the threads.
 * 3. Start the threads to handle the quiz flow for each participant.
 *
 */
public class ClientThread extends Thread {
    // Class-level variables shared among all instances of ClientThread
    static int count = 0, name_count = 0, total_ques = 0, time_per_ques = 0;
    final static Quiz quiz = new Quiz();
    static boolean isAnswering = false;

    // Instance variables specific to each participant thread
    String name;
    int port, score = 0;

    /**
     * Constructs a new ClientThread with the specified port.
     *
     * @param port The port number for the participant.
     */
    ClientThread(int port) {
        this.port = port;
    }

    /**
     * Initializes the total number of questions and time per question for the quiz.
     *
     * @param total_ques     The total number of questions in the quiz.
     * @param time_per_ques  The time limit per question in seconds.
     */
    public static void initialise(int total_ques, int time_per_ques) {
        ClientThread.total_ques = total_ques;
        ClientThread.time_per_ques = time_per_ques;
    }

    /**
     * Overrides the run method from the Thread class, defining the behavior of the thread.
     */
    @Override
    public void run() {
        try {
            // Creates a ServerSocket object bound to the specified port for the server to listen for client connections.
            ServerSocket serverSocket = new ServerSocket(port);
            // Waits for a client to connect and accept the connection, creating a Socket object to communicate with the client.
            Socket socket = serverSocket.accept();
            // Creates a DataOutputStream to send data to the connected client.
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            // Creates a DataInputStream to receive data from the connected client.
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            // Reads the participant's name sent by the client.
            name = dataInputStream.readUTF();
            // Sends the total number of questions and time per question to the client.
            dataOutputStream.writeInt(total_ques);
            dataOutputStream.writeInt(time_per_ques);
            System.out.println(name + " Connected.");
            count++;

            Initiator.initiator();

            // Initiates a loop to handle the quiz questions while the number of answered questions is less than the total number of questions.
                while (quiz.quesNum < total_ques) {
                    // Checks if the thread is the host (port 1000) and reads questions from a file using 'reader.readQues()'.
                    // Otherwise, the thread sleeps for 1000 milliseconds.
                    if (port == 1000) {
                        String[] list = reader.readQues();
                        quiz.Question = list[0];
                        quiz.Option = list[1];
                    } else {
                        Thread.sleep(1000);
                    }
                    // Sends the question to the connected participant using the 'sendQues' method of the 'quiz' object.
                    quiz.sendQues(dataOutputStream, count);

                    // Processes the participant's answer, updating the participant's score.
                    if (port == 1000)
                        isAnswering = true;
                    score += quiz.receiveAns(dataInputStream);
                    if (port == 1000) {
                        isAnswering = false;
                        count = 0;
                        quiz.quesNum++;
                    } else {
                        if (quiz.quesNum == total_ques - 1 && isAnswering)
                            break;
                        Thread.sleep(500);
                    }
                }
                serverSocket.close();
                // Print participants score on Host side
                System.out.println(name + "'s score: " + score);
            } catch(IOException e){
                e.printStackTrace();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

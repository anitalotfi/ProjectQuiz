package QuizOfKings;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author sethsolves: Sara Sharifirad
 *         anitalotfi: Anitta Lotfi
 */
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

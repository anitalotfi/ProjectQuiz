package ProjectAP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * The Client class represents a participant in a quiz connecting to a quiz server.
 * It handles the user's interaction, connects to the server, and communicates
 * information such as participant details, questions, and answers.
 *
 * Usage:
 * 1. Enter Participant ID (1/2/3) when prompted.
 * 2. Connects to the quiz server using the adjusted port number.
 * 3. Provides participant name, receives quiz details, and answers questions.
 * 4. Displays a completion message and exits.
 *
 * Note: This class uses the 'timer' class for handling time-limited user input.
 *
 * @sethsolves: Sara Sharifirad
 * @anitalotfi: Anita Lotfi
 */

class Client {

    /**
     * The main method for the Client class.
     * Initializes variables, connects to the server, handles user interaction,
     * and communicates with the quiz server.
     *
     * @param args Command-line arguments (not used).
     */

    public static void main(String[] args) {
        //initializes a variable 'port' with the base port number.
        int port = 999;
        // prints a message prompting the user to enter their participant ID
        System.out.print("Enter your Participant ID (1/2/3): ");
        //creates a 'Scanner' object for reading input from the console.
        Scanner scanner = new Scanner(System.in);
        //Reads the participant ID entered by the user.
        int id = scanner.nextInt();
        //Consumes the newline character left in the input stream after reading the integer
        scanner.nextLine();
        //adjusts the port number based on the participant ID
        port += id;
        Socket socket = null;
        try {
            //Attempts to create a socket connection to the server ar "localhost" with the adjusted port number.
            socket = new Socket("localhost", port);
        } catch (Exception e) {
            System.out.println("!! ERROR while Connecting to the Server !!");
            System.out.println("** Host has not yet created the Quiz, Host should connect first **");
            //Exits the program with status code 1, indicating an abnormal termination.
            System.exit(1);
        }
        System.out.println("Connected to server..");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //Reads the participant's name to the server using UTF-8 encoding.
            dataOutputStream.writeUTF(name);
            int total_ques = dataInputStream.readInt();
            int time_per_ques = dataInputStream.readInt();
            // Iterates through the questions received from the server.
            for (int i = 0; i < total_ques; i++) {
                //Reads the question from the server.
                String ques = dataInputStream.readUTF();
                //Reads the options for the question from the server.
                String options = dataInputStream.readUTF();
                //Prints the question and options to the console.
                System.out.println(ques);
                System.out.println(options);
                timer t = new timer();
                //getting the participant's answer within the specified time limit
                String ans = t.getInput(time_per_ques);
                //writes the participant's answer to the server.
                dataOutputStream.writeUTF(ans);
            }

            System.out.println("!! You had successfully completed the Quiz !!");
            //TODO: calculating the final score is left
            System.out.println("***Contact the owner for Final Score***");
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
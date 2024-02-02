package ProjectAP;

import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

/**
 * The timer class provides a timer mechanism for user input within a specified time limit.
 *
 * @sethsolves Sara Sharifirad
 * @anitalotfi Anitta Lotfi
 */

public class timer {
    private String str = "", str2 = "";
    BufferedReader in;
    Timer timer = new Timer();
    boolean hasRun = false;

    // TimerTask to define the behavior when the timer runs out.
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            hasRun = true;
            if (str.equals("")) {
                System.out.println("you had enter nothing. Press 'Enter' to proceed to next ques.");
                System.out.println("------------------------------------------");
            }
            timer.cancel();
        }
    };

    /**
     * Gets user input within a specified time limit.
     *
     * @param seconds The time limit in seconds.
     * @return The user's input within the time limit.
     * @throws IOException if an I/O error occurs while reading the input.
     */
    public String getInput(int seconds) throws IOException {
        timer.schedule(task, seconds * 1000);
        System.out.println("Answer within " + seconds + " seconds: ");
        System.out.print("Your ans: ");
        in = new BufferedReader(new InputStreamReader(System.in));
        str2 = in.readLine();
        if (!hasRun) {
            timer.cancel();
            str = str2;
        }
        System.out.println("------------------------------------------");
        return str;
    }

}
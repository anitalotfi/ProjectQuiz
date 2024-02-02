package QuizOfKings;

/**
 * The Set_time class represents a thread for setting the time limit for participants to answer each question.
 *
 * @sethsolves Sara Sharifirad
 * @anitalotfi Anitta Lotfi
 */
class Set_time extends Thread {

    // Variable to store the time limit.
    static protected int t;

    /**
     * Sets the time limit for participants to answer each question.
     *
     * @param time The time limit in seconds.
     */
    static void set(int time) {
        // Prints out the time the participant has entered as input.
        System.out.println("Participants have to answer each question in " + time + " seconds");
        // Sets the time limit.
        t = time;

    }

}

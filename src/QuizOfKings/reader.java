package QuizOfKings;

import java.io.*;
import java.util.Scanner;

/**
 * The reader class provides methods to read questions and answers from files in the quiz application.
 *
 * @sethsolves Sara Sharifirad
 * @anitalotfi Anitta Lotfi
 */

public class reader {

    // Variables to keep track of the current question and manage file input streams and scanners.
    static int currentQues = 0;
    static FileInputStream fis1 = null, fis2 = null;
    static Scanner sc1 = null, sc2 = null;

    /**
     * Reads questions from "Questions.txt" and returns an array containing the question and its options.
     * If 'currentQues' is 0, initializes a 'FileInputStream' and a 'Scanner' for "Questions.txt".
     * Reads two lines from the file, representing the question and options, increments 'currentQues', and returns the array.
     *
     * @return an array of strings containing the question and its options.
     * @throws FileNotFoundException if "Questions.txt" is not found.
     */
    static String[] readQues() throws FileNotFoundException {
        if (currentQues == 0) {
            fis1 = new FileInputStream("ProjectAP/Questions.txt");
            sc1 = new Scanner(fis1);
        }
        String[] list = new String[2];
        list[0] = sc1.nextLine();
        list[1] = sc1.nextLine();
        currentQues++;
        return list;
    }

    /**
     * Reads the answer of the selected question from "AnswerKey.txt" and returns its correct option.
     * If 'currentQues' is 1, initializes a 'FileInputStream' and a 'Scanner' for "AnswerKey.txt".
     * Reads the answer from the file and returns it.
     *
     * @return the correct option for the selected question.
     * @throws FileNotFoundException if "AnswerKey.txt" is not found.
     */
    static String readAns() throws FileNotFoundException {
        if (currentQues == 1) {
            fis2 = new FileInputStream("ProjectAP/AnswerKey.txt");
            sc2 = new Scanner(fis2);
        }
        String ans = sc2.nextLine();
        return ans;
    }
}
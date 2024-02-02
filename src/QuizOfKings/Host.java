package QuizOfKings;

/**
 * The abstract Host class represents a host in a quiz setup, providing methods to interact with various components.
 * It includes calls to select a subject, set questions, and configure the quiz time.
 *
 * @sethsolves Sara Sharifirad
 * @anitalotfi Anitta Lotfi
 */

abstract class Host {

    /**
     * Calls the subject selector to choose a subject for the quiz.
     * TODO: The method 'subject()' currently calls 'subject_selector.select_subjet()' without returning or utilizing the result.
     */
    void subject() {
        subject_selector.select_subjet();
    }

    /**
     * Calls the question setter to set up quiz questions.
     *
     * @return true if questions are set successfully, false otherwise.
     */
    boolean questions() {
        return question_setter.set_questions();
    }

    /**
     * Calls the Set_time class to configure the quiz time.
     * TODO: The usage of 'wait' in the 'Set_time.set(wait)' method is unclear from the provided code comments.
     *
     * @param wait The time to configure for the quiz.
     */
    public void time(int wait) {
        Set_time.set(wait);
    }

}


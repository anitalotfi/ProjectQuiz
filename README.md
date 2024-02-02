# Quiz Application

## Overview

This is a simple quiz application implemented in Java, designed to facilitate quiz sessions between a host and participants. The application involves a server-hosted quiz with multiple participants connecting to answer questions within a specified time limit.

## Features

- Host-Participant Interaction: The host initiates the quiz, and participants connect to answer questions.
- Subject Selection: The host can select a quiz subject from options such as Programming, General Knowledge, and Science.
- Question Setting: Questions are dynamically set by the host based on the selected subject.
- Timer Functionality: Participants are given a specific time limit to answer each question.
- Scoring: The host can view and calculate the participants' scores at the end of the quiz.

## Usage

1. Host Setup:
   - Execute Main.java to initiate the host interface.
   - Select a quiz subject.
   - Set the number of questions and time limit per question.
   - Wait for participants to connect.

2. Participant Interaction:
   - Execute Client.java to connect participants to the quiz.
   - Participants enter their names and answer questions within the time limit.

3. Completion:
   - After all questions are answered, the host can calculate and view participants' scores.

## Dependencies

- Java (JDK 8 or higher)

## Contributors

- [Sara Sharifirad](https://github.com/sethsolves)
- [Anitta Lotfi](https://github.com/anitalotfi)

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

Special thanks to the contributors and those who provided feedback during the development of this quiz application.

# Elf — Java Desktop Chatbot

Elf is a simple, rule-based desktop chatbot built with **Java Swing**. It provides a graphical chat interface and demonstrates basic chatbot logic, file-based persistence, mood-keyword detection, reminders, to-do lists, and customizable bot naming.

> **Project type:** Educational / personal desktop application  
> **Current implementation:** Rule-based responses (not an LLM or generative AI model)

## Features

- **Chat interface:** Send messages through a Java Swing desktop window.
- **Basic conversation:** Responds to greetings, help requests, and common phrases.
- **Date and time:** Displays the current date and time.
- **Mood keyword detection:** Recognizes selected positive and negative mood-related words and responds with predefined messages.
- **Life-story notes:** Saves text entered with `remember this` to a local file.
- **To-do list:** Adds and displays simple tasks.
- **Reminders:** Saves reminder text to a local file.
- **Custom bot name:** Allows the user to rename the chatbot.
- **Chat history:** Saves conversations locally between runs.

## Built With

- Java
- Java Swing (GUI)
- Java I/O and local text-file storage

## Requirements

- JDK 8 or newer (the project uses standard Java and Swing APIs)
- A desktop environment capable of running Swing applications

## Run the Project

### Option 1: Compile and run from the terminal

1. Clone the repository:

   ```bash
   git clone https://github.com/YOUR-USERNAME/Elf-Chatbot.git
   cd Elf-Chatbot
   ```

2. Compile the Java source files:

   ```bash
   javac -d out src/*.java
   ```

3. Run the application:

   ```bash
   java -cp out ElfChatBot
   ```

### Option 2: Run in IntelliJ IDEA

1. Open the cloned project folder in IntelliJ IDEA.
2. Make sure a JDK is configured for the project.
3. Mark `src` as a Sources Root if needed.
4. Open `src/ElfChatBot.java`.
5. Run the `main` method.

## Example Commands

The chatbot recognizes phrases such as:

| Try saying | Expected behavior |
|---|---|
| `hello` | Greets the user |
| `what is your name` | Displays the bot's current name |
| `what time is it` | Displays the current time |
| `what is the date today` | Displays today's date |
| `remember this buy groceries` | Saves a life-story note locally |
| `add to-do finish assignment` | Adds a task |
| `show to-do` | Displays saved tasks |
| `remind me to call home` | Saves a reminder |
| `motivate me` | Displays a motivational message |
| `change your name to Nova` | Changes the bot's name |

Commands are matched using simple text rules. Exact wording may matter for some commands.

## Project Structure

```text
Elf-Chatbot/
├── src/
│   ├── ChatBotLogic.java
│   ├── ElfChatBot.java
│   ├── MemoryManager.java
│   ├── MoodDetector.java
│   ├── Motivator.java
│   ├── PasswordManager.java
│   └── ReminderManager.java
├── .gitignore
├── LICENSE
└── README.md
```

## Local Data and Privacy

When you run the application, it may create local text files in the working directory to store chat history, memories, reminders, tasks, and the bot's name. These files are intentionally excluded from this repository because they may contain personal information.

Do not commit personal conversations, passwords, tokens, private reminders, or other sensitive data.

## Security Limitations

- The current `PasswordManager` encodes passwords using Base64. **Base64 is reversible encoding, not encryption**, and must not be used to protect real passwords.
- Do not enter real account passwords into this application.
- The `remember this` save flow and `what do you remember` recall flow currently use different files, so saved life-story notes are not shown by the recall command. This should be fixed in a future version.
- The current mood detection is keyword-based and is not a mental-health assessment or a substitute for professional support.
- Local text files are not encrypted and have no multi-user access control.
- The application is an educational prototype, not a production-ready secure assistant.

## Known Limitations and Future Improvements

- Replace keyword matching with a more flexible conversation engine or an AI model/API.
- Improve input parsing and command handling.
- Add a proper data-storage layer and user-controlled data deletion.
- Add secure authentication and remove the password-storage feature unless it is redesigned using established password-management practices.
- Escape user-provided text before displaying it as Swing HTML to avoid unintended HTML rendering.
- Improve GUI layout, accessibility, and error handling.
- Add automated tests and clearer setup documentation.

## License

All Rights Reserved. See [LICENSE](LICENSE) for details. No permission to copy, modify, distribute, or use this project commercially is granted by default.

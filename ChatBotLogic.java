import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatBotLogic {
    public static String getResponse(String input, String botName) {
        input = input.toLowerCase();

        if (input.matches(".*\\b(hi|hello|hey)\\b.*")) return "Hey! How can I help you today?";
        if (input.contains("your name")) return "My name is " + botName + ".";
        if (input.contains("how are you")) return "I'm doing great! How about you?";
        if (input.contains("bye")) return "Goodbye! Take care.";
        if (input.contains("help")) return "Try asking me for time, date, to-do help, reminders, or change my name!";

        // Time and date
        if (input.contains("time and date") || input.contains("date and time")) return getCurrentDateTime();
        if (input.contains("time")) return getCurrentTime();
        if (input.contains("date")) return getCurrentDate();

        // Memory
        if (input.contains("remember this")) return MemoryManager.save(input.substring(13).trim());
        if (input.contains("what do you remember")) return MemoryManager.recall();

        // To-do
        if (input.startsWith("add to-do ")) return ReminderManager.saveToDo(input.substring(10).trim());
        if (input.equals("show to-do")) return ReminderManager.readToDo();

        // Reminder
        if (input.contains("remind me")) return ReminderManager.save(input);

        // Password
        if (input.startsWith("save password")) {
            String[] parts = input.split(" ", 3);
            return PasswordManager.save("default", parts.length > 2 ? parts[2] : "none");
        }
        if (input.equals("show password")) return PasswordManager.retrieve("default");

        // Mood detection
        String moodResponse = MoodDetector.detectMood(input);
        if (moodResponse != null) return moodResponse;

        if (MoodDetector.moodFollowUp && input.equals("yes")) {
            return "I'm listening. Tell me what's on your mind.";
        }

        if (MoodDetector.moodFollowUp) {
            return MoodDetector.consoleUser(input);
        }

        if (MoodDetector.happyFollowUp && !input.contains("happy")) {
            return MoodDetector.celebrateHappiness(input);
        }

        // Motivation
        if (input.equals("motivate me")) return Motivator.getQuote();

        return "I'm still learning. Can you rephrase that?";
    }

    public static void saveToFile(String line) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("chat_history.txt", true)))) {
            out.println(line);
        } catch (IOException e) {
            System.out.println("Error saving chat: " + e.getMessage());
        }
    }

    public static List<String> loadHistory() {
        List<String> history = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("chat_history.txt"))) {
            String line;
            while ((line = br.readLine()) != null) history.add(line);
        } catch (IOException ignored) {}
        return history;
    }

    public static boolean isLifeStoryCommand(String input) {
        return input.toLowerCase().contains("life story") || input.toLowerCase().contains("remember this");
    }

    public static void saveLifeStory(String content) {
        try (FileWriter fw = new FileWriter("life_story.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(content);
        } catch (IOException e) {
            System.out.println("Failed to save life story: " + e.getMessage());
        }
    }
    private static String getCurrentTime() {
        return "The current time is " + new SimpleDateFormat("hh:mm:ss a").format(new Date()) + ".";
    }

    private static String getCurrentDate() {
        return "Today's date is " + new SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date()) + ".";
    }

    private static String getCurrentDateTime() {
        return getCurrentTime() + " " + getCurrentDate();
    }
}

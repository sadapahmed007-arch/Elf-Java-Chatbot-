import java.io.*;
public class ReminderManager {
    public static String save(String text) {
        try (PrintWriter out = new PrintWriter(new FileWriter("reminders.txt", true))) {
            out.println(text);
            return "Reminder noted!";
        } catch (IOException e) {
            return "Failed to save reminder.";
        }
    }

    public static String saveToDo(String task) {
        try (PrintWriter out = new PrintWriter(new FileWriter("to-do.txt", true))) {
            out.println(task);
            return "Task added to your to-do list.";
        } catch (IOException e) {
            return "Couldn't save to-do.";
        }
    }

    public static String readToDo() {
        StringBuilder sb = new StringBuilder("Your To-Do List:\n");
        try (BufferedReader reader = new BufferedReader(new FileReader("to-do.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) sb.append("- ").append(line).append("\n");
        } catch (IOException e) {
            return "No to-do list found.";
        }
        return sb.toString();
    }
}

import java.io.*;
public class MemoryManager {
    public static String save(String memory) {
        try (PrintWriter out = new PrintWriter(new FileWriter("memories.txt", true))) {
            out.println(memory);
            return "Saved to memory!";
        } catch (IOException e) {
            return "Error saving memory.";
        }
    }

    public static String recall() {
        StringBuilder sb = new StringBuilder("Here's what I remember:\n");
        try (BufferedReader reader = new BufferedReader(new FileReader("memories.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) sb.append("- ").append(line).append("\n");
        } catch (IOException e) {
            return "Nothing in memory.";
        }
        return sb.toString();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class ElfChatBot {
    private JFrame frame;
    private JTextField userInput;
    private JButton sendButton;
    private JPanel chatPanel;
    private JScrollPane scrollPane;
    private String botName;
    private boolean pendingRename = false;
    private ArrayList<String> chatHistory = new ArrayList<>();

    public ElfChatBot() {
        botName = loadBotName();
        frame = new JFrame( botName + " ChatBot");
        frame.setSize(450, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel inputPanel = new JPanel(new BorderLayout());
        userInput = new JTextField();
        sendButton = new JButton("Send");

        inputPanel.add(userInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        sendButton.addActionListener(e -> handleUserInput());
        userInput.addActionListener(e -> handleUserInput());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        loadChatHistory();

        frame.setVisible(true);
    }

    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;

        addChatBubble(input, true);
        chatHistory.add("You: " + input);
        saveToFile("You: " + input);

        if (pendingRename) {
            botName = input;
            saveBotName(botName);
            String reply = "Nice! You can now call me " + botName + ".";
            addChatBubble(reply, false);
            chatHistory.add(botName + ": " + reply);
            saveToFile(botName + ": " + reply);
            pendingRename = false;
            userInput.setText("");
            return;
        }

        // Name changing logic
        String lowerInput = input.toLowerCase();
        if (lowerInput.contains("change your name") || lowerInput.contains("call you by another name")
                || lowerInput.contains("rename you") || lowerInput.contains("give you a new name")) {
            String[] parts = input.split("to ");
            if (parts.length > 1) {
                botName = parts[1].trim();
                saveBotName(botName);
                String reply = "Okay, from now on, call me " + botName + "!";
                addChatBubble(reply, false);
                chatHistory.add(botName + ": " + reply);
                saveToFile(botName + ": " + reply);
            } else {
                pendingRename = true;
                String reply = "Sure! What name would you like to give me?";
                addChatBubble(reply, false);
                chatHistory.add(botName + ": " + reply);
                saveToFile(botName + ": " + reply);
            }
            userInput.setText("");
            return;
        }

        if (ChatBotLogic.isLifeStoryCommand(input)) {
            ChatBotLogic.saveLifeStory(input);
            String reply = "Got it! I've saved that part of your life story.";
            addChatBubble(reply, false);
            chatHistory.add(botName + ": " + reply);
            saveToFile(botName + ": " + reply);
            userInput.setText("");
            return;
        }

        // Check for mood
        String moodReply = MoodDetector.detectMood(input);
        if (moodReply != null) {
            addChatBubble(moodReply, false);
            ChatBotLogic.saveToFile(botName + ": " + moodReply);
            userInput.setText("");
            return;
        }

        // Handle logic
        String response = ChatBotLogic.getResponse(input, botName);
        addChatBubble(response, false);
        ChatBotLogic.saveToFile(botName + ": " + response);

        userInput.setText("");
        chatPanel.revalidate();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    private void addChatBubble(String text, boolean isUser) {
        JPanel bubble = new JPanel(new BorderLayout());
        JLabel label = new JLabel("<html><p style='width: 250px;'>" + text + "</p></html>");
        label.setOpaque(true);
        label.setBackground(isUser ? new Color(200, 230, 201) : new Color(240, 240, 240));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bubble.add(label, isUser ? BorderLayout.EAST : BorderLayout.WEST);
        chatPanel.add(bubble);
    }

    private void loadChatHistory() {
        for (String line : ChatBotLogic.loadHistory()) {
            boolean isUser = line.startsWith("You:");
            String msg = line.replace("You: ", "").replace(botName + ": ", "");
            addChatBubble(msg, isUser);
        }
    }

    private void saveToFile(String line) {
        try (FileWriter fw = new FileWriter("chat_history.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(line);
        } catch (IOException e) {
            System.out.println("Failed to save chat: " + e.getMessage());
        }
    }

    private String loadBotName() {
        File file = new File("bot_name.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String name = br.readLine();
                if (name != null && !name.trim().isEmpty()) return name.trim();
            } catch (IOException e) {
                System.out.println("Failed to load bot name: " + e.getMessage());
            }
        }
        return "Elf"; // Default name
    }

    private void saveBotName(String name) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bot_name.txt")))) {
            out.println(name);
        } catch (IOException e) {
            System.out.println("Failed to save bot name: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ElfChatBot::new);
    }
}

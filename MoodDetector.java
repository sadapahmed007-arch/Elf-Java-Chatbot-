import java.util.Random;
public class MoodDetector {
    public static boolean moodFollowUp = false;
    public static boolean happyFollowUp = false;

    private static final String[] consolations = {
            "I'm really sorry to hear that. Just know that you're not alone and things will get better.",
            "Sometimes life gets hard, but you're stronger than you think. Keep going.",
            "That sounds tough. I'm here if you need someone to talk to.",
            "Take a deep breath. Things will work out in time. You're not alone.",
            "Even the darkest nights end with a sunrise. You're doing your best, and that matters.",
            "It’s okay to feel down sometimes. Just remember, you're cared for and supported."
    };

    private static final String[] happyReplies = {
            "Yay! That made my day too!",
            "That's awesome to hear! I'm smiling with you!",
            "So happy for you! Keep spreading that joy!",
            "That’s such great news! Let’s celebrate!",
            "Woo! Moments like these are precious. Enjoy every bit of it!"
    };

    public static String detectMood(String input) {
        input = input.toLowerCase();

        if (input.contains("sad") || input.contains("tired") || input.contains("upset") || input.contains("depressed")) {
            moodFollowUp = true;
            return "I'm here for you. Want to talk about it?";
        }

        if (input.contains("happy") || input.contains("excited") || input.contains("great")) {
            happyFollowUp = true;
            return "That’s wonderful! Why are you happy?";
        }

        return null;
    }

    public static String consoleUser(String userInput) {
        moodFollowUp = false;
        return getRandom(consolations);
    }

    public static String celebrateHappiness(String userInput) {
        happyFollowUp = false;
        return getRandom(happyReplies);
    }

    private static String getRandom(String[] arr) {
        return arr[new Random().nextInt(arr.length)];
    }
}

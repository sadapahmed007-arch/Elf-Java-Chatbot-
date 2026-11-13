import java.util.Random;
public class Motivator {
    private static final String[] quotes = {
            "Keep going, you're doing great!",
            "Believe in yourself!",
            "You are capable of amazing things.",
            "Don't give up. The beginning is always the hardest.",
            "You can do it!",
            "Every step counts.",
            "Success starts with courage.",
            "Keep pushing forward.",
            "You are stronger than you think!"
    };

    public static String getQuote() {
        return quotes[new Random().nextInt(quotes.length)];
    }
}

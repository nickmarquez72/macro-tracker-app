public class QuickInputParser {
    private String foodQuery;
    private double amount;
    private boolean amountIsGrams;

    public QuickInputParser(String foodQuery, double amount, boolean amountIsGrams) {
        this.foodQuery = foodQuery;
        this.amount = amount;
        this.amountIsGrams = amountIsGrams;
    }

    public String getFoodQuery() {
        return foodQuery;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isAmountGrams() {
        return amountIsGrams;
    }

    public static QuickInputParser parse(String input) {
        if (input == null) {
            return new QuickInputParser("", 1, false);
        }

        String text = input.trim().toLowerCase();

        if (text.isEmpty()) {
            return new QuickInputParser("", 1, false);
        }

        text = text.replaceAll("\\s+", " ");

        if (text.matches("^\\d+(\\.\\d+)?g\\s+.+$")) {
            int gIndex = text.indexOf('g');
            double grams = Double.parseDouble(text.substring(0, gIndex));
            String food = text.substring(gIndex + 1).trim();
            return new QuickInputParser(food, grams, true);
        }

        if (text.matches("^\\d+(\\.\\d+)?\\s*g\\s+.+$")) {
            String[] parts = text.split("\\s+", 3);
            double grams = Double.parseDouble(parts[0]);
            String food = parts[2].trim();
            return new QuickInputParser(food, grams, true);
        }

        if (text.matches("^\\d+(\\.\\d+)?\\s+.+$")) {
            String[] parts = text.split("\\s+", 2);
            double servings = Double.parseDouble(parts[0]);
            String food = parts[1].trim();
            return new QuickInputParser(food, servings, false);
        }

        return new QuickInputParser(text, 1, false);
    }
}

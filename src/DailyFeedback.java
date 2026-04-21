public class DailyFeedback {

    public static String getAvatarMood(double protein, double calories, double proteinGoal, double calorieGoal) {
        double proteinRatio = ratio(protein, proteinGoal);
        double calorieRatio = ratio(calories, calorieGoal);

        if (proteinRatio >= 1.0 && calorieRatio >= 0.8 && calorieRatio <= 1.1) {
            return "Strong";
        }

        if (proteinRatio >= 0.8) {
            return "Focused";
        }

        if (calorieRatio < 0.5) {
            return "Low Energy";
        }

        return "Balanced";
    }

    public static String getAvatarFace(double protein, double calories, double proteinGoal, double calorieGoal) {
        String mood = getAvatarMood(protein, calories, proteinGoal, calorieGoal);

        switch (mood) {
            case "Strong":
                return "ᕙ(•̀‸•́‶)ᕗ";
            case "Focused":
                return "•̀ᴗ•́";
            case "Low Energy":
                return "•︵•";
            default:
                return "◕‿◕";
        }
    }

    public static String getFeedback(double calories, double protein, double carbs, double fat,
                                     double calorieGoal, double proteinGoal, double carbGoal, double fatGoal) {

        double proteinLeft = proteinGoal - protein;
        double caloriesLeft = calorieGoal - calories;
        double fatOver = fat - fatGoal;

        if (protein < proteinGoal * 0.6) {
            return "You’re behind on protein. A lean protein source would help a lot right now.";
        }

        if (fatOver > 10) {
            return "Fat is running high. Try a leaner food choice for your next meal.";
        }

        if (caloriesLeft > 400 && protein >= proteinGoal * 0.8) {
            return "Calories are still open and protein looks solid. You have room for a balanced meal.";
        }

        if (protein >= proteinGoal && calories <= calorieGoal) {
            return "Great job — protein goal hit and calories are under control.";
        }

        if (calories > calorieGoal) {
            return "Calories are a little high. Keep the rest of the day lighter and protein-focused.";
        }

        if (carbs < carbGoal * 0.5 && calories < calorieGoal * 0.7) {
            return "You’re still pretty low overall. A meal with carbs and protein would fit well.";
        }

        return "You’re on track. Keep building the day with balanced, high-protein meals.";
    }

    public static int getDailyScore(double calories, double protein, double carbs, double fat,
                                    double calorieGoal, double proteinGoal, double carbGoal, double fatGoal) {

        double calorieScore = closeness(calories, calorieGoal);
        double proteinScore = ratio(protein, proteinGoal);
        double carbScore = closeness(carbs, carbGoal);
        double fatScore = closeness(fat, fatGoal);

        double weighted =
                calorieScore * 0.30 +
                        proteinScore * 0.40 +
                        carbScore * 0.15 +
                        fatScore * 0.15;

        int score = (int) Math.round(weighted * 100);
        return Math.max(0, Math.min(score, 100));
    }

    private static double ratio(double value, double goal) {
        if (goal <= 0) return 0;
        return Math.max(0, Math.min(value / goal, 1.0));
    }

    private static double closeness(double value, double goal) {
        if (goal <= 0) return 0;

        double difference = Math.abs(value - goal);
        double result = 1.0 - (difference / goal);

        return Math.max(0, Math.min(result, 1.0));
    }
}
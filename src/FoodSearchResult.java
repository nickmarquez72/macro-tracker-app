public class FoodSearchResult {
    public String name;
    public double calories;
    public double protein;
    public double carbs;
    public double fat;
    public double servingSizeGrams;

    @Override
    public String toString() {
        return name + " | Calories: " + calories
                + " | Protein: " + protein
                + " | Carbs: " + carbs
                + " | Fat: " + fat;
    }
}
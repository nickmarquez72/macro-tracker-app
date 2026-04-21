public class FoodItem {
    private String name;
    private double servingSizeGrams;
    private double caloriesPerServing;
    private double proteinPerServing;
    private double carbsPerServing;
    private double fatPerServing;

    public FoodItem(String name, double servingSizeGrams,
                    double caloriesPerServing, double proteinPerServing,
                    double carbsPerServing, double fatPerServing) {
        this.name = name;
        this.servingSizeGrams = servingSizeGrams;
        this.caloriesPerServing = caloriesPerServing;
        this.proteinPerServing = proteinPerServing;
        this.carbsPerServing = carbsPerServing;
        this.fatPerServing = fatPerServing;
    }

    public String getName() {
        return name;
    }

    public double getServingSizeGrams() {
        return servingSizeGrams;
    }

    public double getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public double getProteinPerServing() {
        return proteinPerServing;
    }

    public double getCarbsPerServing() {
        return carbsPerServing;
    }

    public double getFatPerServing() {
        return fatPerServing;
    }
}
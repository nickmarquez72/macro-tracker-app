public class FoodEntry
{
    private String name;
    private double servings;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;

    public FoodEntry()
    {
    }

    public FoodEntry(String name, double servings, double calories, double protein, double carbs, double fat)
    {
        this.name = name;
        this.servings = servings;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public String getName()
    {
        return name;
    }

    public double getServings()
    {
        return servings;
    }

    public double getCalories()
    {
        return calories;
    }

    public double getProtein()
    {
        return protein;
    }

    public double getCarbs()
    {
        return carbs;
    }

    public double getFat()
    {
        return fat;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setServings(double servings)
    {
        this.servings = servings;
    }

    public void setCalories(double calories)
    {
        this.calories = calories;
    }

    public void setProtein(double protein)
    {
        this.protein = protein;
    }

    public void setCarbs(double carbs)
    {
        this.carbs = carbs;
    }

    public void setFat(double fat)
    {
        this.fat = fat;
    }

    @Override
    public String toString()
    {
        return name
                + "  |  " + servings + " serving(s)"
                + "  |  " + calories + " cal"
                + "  |  P: " + protein + "g"
                + "  C: " + carbs + "g"
                + "  F: " + fat + "g";
    }
}
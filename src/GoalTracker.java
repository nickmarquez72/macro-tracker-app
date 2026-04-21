public class GoalTracker {
    private double calorieGoal;
    private double proteinGoal;
    private double carbGoal;
    private double fatGoal;

    public GoalTracker(double calorieGoal, double proteinGoal, double carbGoal, double fatGoal) {
        this.calorieGoal = calorieGoal;
        this.proteinGoal = proteinGoal;
        this.carbGoal = carbGoal;
        this.fatGoal = fatGoal;
    }

    public double getCalorieGoal() {
        return calorieGoal;
    }

    public double getProteinGoal() {
        return proteinGoal;
    }

    public double getCarbGoal() {
        return carbGoal;
    }

    public double getFatGoal() {
        return fatGoal;
    }

    public void setCalorieGoal(double calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public void setProteinGoal(double proteinGoal) {
        this.proteinGoal = proteinGoal;
    }

    public void setCarbGoal(double carbGoal) {
        this.carbGoal = carbGoal;
    }

    public void setFatGoal(double fatGoal) {
        this.fatGoal = fatGoal;
    }
}
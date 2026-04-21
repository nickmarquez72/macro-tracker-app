import java.util.ArrayList;

public class MacroTrackerData
{
    private ArrayList<FoodEntry> entries;

    public MacroTrackerData()
    {
        entries = new ArrayList<>();
    }

    public ArrayList<FoodEntry> getEntries()
    {
        return entries;
    }

    public void setEntries(ArrayList<FoodEntry> entries)
    {
        this.entries = entries;
    }

    public void addEntry(FoodEntry entry)
    {
        entries.add(entry);
    }

    public void removeEntry(FoodEntry entry)
    {
        entries.remove(entry);
    }

    public double getTotalCalories()
    {
        double total = 0;
        for (FoodEntry entry : entries)
            total += entry.getCalories();
        return total;
    }

    public double getTotalProtein()
    {
        double total = 0;
        for (FoodEntry entry : entries)
            total += entry.getProtein();
        return total;
    }

    public double getTotalCarbs()
    {
        double total = 0;
        for (FoodEntry entry : entries)
            total += entry.getCarbs();
        return total;
    }

    public double getTotalFat()
    {
        double total = 0;
        for (FoodEntry entry : entries)
            total += entry.getFat();
        return total;
    }
}
import java.io.*;
import java.time.LocalDate;

public class StreakTracker {
    private static final String FILE_NAME = "streak.txt";

    private int streakCount;
    private LocalDate lastLoggedDate;

    public StreakTracker() {
        streakCount = 0;
        lastLoggedDate = null;
    }

    public int getStreakCount() {
        return streakCount;
    }

    public LocalDate getLastLoggedDate() {
        return lastLoggedDate;
    }

    public void recordLogToday() {
        LocalDate today = LocalDate.now();

        if (lastLoggedDate == null) {
            streakCount = 1;
            lastLoggedDate = today;
            save();
            return;
        }

        if (lastLoggedDate.equals(today)) {
            return;
        }

        if (lastLoggedDate.equals(today.minusDays(1))) {
            streakCount++;
        } else {
            streakCount = 1;
        }

        lastLoggedDate = today;
        save();
    }

    public static StreakTracker load() {
        StreakTracker tracker = new StreakTracker();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return tracker;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String streakLine = reader.readLine();
            String dateLine = reader.readLine();

            if (streakLine != null && !streakLine.isBlank()) {
                tracker.streakCount = Integer.parseInt(streakLine.trim());
            }

            if (dateLine != null && !dateLine.isBlank()) {
                tracker.lastLoggedDate = LocalDate.parse(dateLine.trim());
            }
        } catch (Exception e) {
            System.out.println("Could not load streak data.");
        }

        return tracker;
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.println(streakCount);
            writer.println(lastLoggedDate == null ? "" : lastLoggedDate);
        } catch (IOException e) {
            System.out.println("Could not save streak data.");
        }
    }
}
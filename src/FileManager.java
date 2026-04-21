import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

public class FileManager
{
    private static final String FILE_NAME = "macro_data.json";

    public static void save(MacroTrackerData data)
    {
        try
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = new FileWriter(FILE_NAME);
            gson.toJson(data, writer);
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static MacroTrackerData load()
    {
        try
        {
            Gson gson = new Gson();
            Reader reader = new FileReader(FILE_NAME);
            MacroTrackerData data = gson.fromJson(reader, MacroTrackerData.class);
            reader.close();

            if (data == null)
                return new MacroTrackerData();

            if (data.getEntries() == null)
                data.setEntries(new java.util.ArrayList<>());

            return data;
        }
        catch (Exception e)
        {
            return new MacroTrackerData();
        }
    }
}
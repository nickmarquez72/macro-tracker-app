import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenFoodFactsClient {

    public static FoodSearchResult searchByBarcode(String barcode)
            throws IOException, InterruptedException {

        String url = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();

        if (response.statusCode() != 200 || body == null || body.trim().isEmpty() || !body.trim().startsWith("{")) {
            return null;
        }

        JsonObject root;
        try {
            root = JsonParser.parseString(body).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }

        if (!root.has("status") || root.get("status").getAsInt() == 0) {
            return null;
        }

        if (!root.has("product") || root.get("product").isJsonNull()) {
            return null;
        }

        JsonObject product = root.getAsJsonObject("product");
        return buildResultFromProduct(product);
    }

    public static ArrayList<FoodSearchResult> searchByName(String foodName)
            throws IOException, InterruptedException {

        String query = URLEncoder.encode(foodName, StandardCharsets.UTF_8);
        String url = "https://world.openfoodfacts.org/cgi/search.pl?search_terms="
                + query
                + "&search_simple=1&action=process&json=1&page_size=5";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<FoodSearchResult> results = new ArrayList<>();

        String body = response.body();
        if (response.statusCode() != 200 || body == null || body.trim().isEmpty() || !body.trim().startsWith("{")) {
            return results;
        }

        JsonObject root;
        try {
            root = JsonParser.parseString(body).getAsJsonObject();
        } catch (Exception e) {
            return results;
        }

        if (!root.has("products") || root.get("products").isJsonNull()) {
            return results;
        }

        JsonArray products = root.getAsJsonArray("products");
        for (JsonElement element : products) {
            JsonObject product = element.getAsJsonObject();
            FoodSearchResult result = buildResultFromProduct(product);
            if (result != null && result.name != null && !result.name.equals("Unknown")) {
                results.add(result);
            }
        }

        return results;
    }

    private static FoodSearchResult buildResultFromProduct(JsonObject product) {
        FoodSearchResult result = new FoodSearchResult();
        result.name = getString(product, "product_name");

        JsonObject nutriments = product.getAsJsonObject("nutriments");
        if (nutriments != null) {
            result.calories = getDouble(nutriments, "energy-kcal_100g");
            result.protein = getDouble(nutriments, "proteins_100g");
            result.carbs = getDouble(nutriments, "carbohydrates_100g");
            result.fat = getDouble(nutriments, "fat_100g");
        }

        String servingText = getString(product, "serving_size");
        result.servingSizeGrams = extractGrams(servingText);

        return result;
    }

    private static String getString(JsonObject obj, String key) {
        if (obj.has(key) && !obj.get(key).isJsonNull()) {
            return obj.get(key).getAsString();
        }
        return "Unknown";
    }

    private static double getDouble(JsonObject obj, String key) {
        try {
            if (obj.has(key) && !obj.get(key).isJsonNull()) {
                return obj.get(key).getAsDouble();
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    private static double extractGrams(String servingText) {
        if (servingText == null || servingText.equals("Unknown")) {
            return 0;
        }

        String lower = servingText.toLowerCase();
        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*g");
        Matcher matcher = pattern.matcher(lower);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }

        return 0;
    }
}
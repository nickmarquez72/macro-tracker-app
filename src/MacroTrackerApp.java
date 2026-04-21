import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MacroTrackerApp extends Application
{
    private MacroTrackerData data;
    private ObservableList<FoodEntry> observableEntries;

    private Label caloriesLabel;
    private Label proteinLabel;
    private Label carbsLabel;
    private Label fatLabel;
    private Label entryCountLabel;

    @Override
    public void start(Stage stage)
    {
        data = FileManager.load();
        observableEntries = FXCollections.observableArrayList(data.getEntries());

        Label appTitle = new Label("Macro Tracker");
        appTitle.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label("Track meals, monitor macros, and save progress");
        subtitle.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #6b7280;"
        );

        VBox titleBox = new VBox(4, appTitle, subtitle);
        titleBox.setPadding(new Insets(0, 0, 10, 0));

        TextField nameField = createTextField("Food name");
        TextField servingsField = createTextField("Servings");
        TextField caloriesField = createTextField("Calories");
        TextField proteinField = createTextField("Protein (g)");
        TextField carbsField = createTextField("Carbs (g)");
        TextField fatField = createTextField("Fat (g)");

        Label formTitle = new Label("Add Food");
        formTitle.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #111827;"
        );

        Label formHint = new Label("Enter the food and its macro values");
        formHint.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #6b7280;"
        );

        Button addButton = createPrimaryButton("Add Entry");
        Button deleteButton = createSecondaryButton("Delete Selected");
        Button saveButton = createSecondaryButton("Save Data");

        HBox buttonRow = new HBox(10, addButton, deleteButton, saveButton);
        buttonRow.setAlignment(Pos.CENTER_LEFT);

        VBox formCard = new VBox(
                12,
                formTitle,
                formHint,
                nameField,
                servingsField,
                caloriesField,
                proteinField,
                carbsField,
                fatField,
                buttonRow
        );
        formCard.setPadding(new Insets(20));
        formCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 18;" +
                        "-fx-border-color: #e5e7eb;" +
                        "-fx-border-radius: 18;"
        );
        formCard.setPrefWidth(320);

        Label listTitle = new Label("Entries");
        listTitle.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #111827;"
        );

        entryCountLabel = new Label();
        entryCountLabel.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #6b7280;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox listHeader = new HBox(10, listTitle, spacer, entryCountLabel);
        listHeader.setAlignment(Pos.CENTER_LEFT);

        ListView<FoodEntry> listView = new ListView<>(observableEntries);
        listView.setPrefHeight(300);
        listView.setStyle(
                "-fx-background-radius: 14;" +
                        "-fx-border-radius: 14;" +
                        "-fx-border-color: #e5e7eb;" +
                        "-fx-control-inner-background: white;" +
                        "-fx-font-size: 13px;"
        );

        Label totalsTitle = new Label("Today’s Totals");
        totalsTitle.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #111827;"
        );

        caloriesLabel = createMetricLabel();
        proteinLabel = createMetricLabel();
        carbsLabel = createMetricLabel();
        fatLabel = createMetricLabel();

        VBox caloriesCard = createStatCard("Calories", caloriesLabel);
        VBox proteinCard = createStatCard("Protein", proteinLabel);
        VBox carbsCard = createStatCard("Carbs", carbsLabel);
        VBox fatCard = createStatCard("Fat", fatLabel);

        HBox statRow1 = new HBox(15, caloriesCard, proteinCard);
        HBox statRow2 = new HBox(15, carbsCard, fatCard);

        VBox rightCard = new VBox(
                18,
                listHeader,
                listView,
                new Separator(),
                totalsTitle,
                statRow1,
                statRow2
        );
        rightCard.setPadding(new Insets(20));
        rightCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 18;" +
                        "-fx-border-color: #e5e7eb;" +
                        "-fx-border-radius: 18;"
        );

        HBox.setHgrow(rightCard, Priority.ALWAYS);

        HBox mainContent = new HBox(20, formCard, rightCard);
        mainContent.setAlignment(Pos.TOP_LEFT);

        VBox rootContent = new VBox(20, titleBox, mainContent);
        rootContent.setPadding(new Insets(25));
        rootContent.setStyle("-fx-background-color: #f8fafc;");

        updateTotals();

        addButton.setOnAction(e ->
        {
            try
            {
                String name = nameField.getText().trim();

                if (name.isEmpty())
                {
                    showAlert("Please enter a food name.");
                    return;
                }

                double servings = Double.parseDouble(servingsField.getText().trim());
                double calories = Double.parseDouble(caloriesField.getText().trim());
                double protein = Double.parseDouble(proteinField.getText().trim());
                double carbs = Double.parseDouble(carbsField.getText().trim());
                double fat = Double.parseDouble(fatField.getText().trim());

                FoodEntry entry = new FoodEntry(name, servings, calories, protein, carbs, fat);

                data.addEntry(entry);
                observableEntries.add(entry);
                updateTotals();

                nameField.clear();
                servingsField.clear();
                caloriesField.clear();
                proteinField.clear();
                carbsField.clear();
                fatField.clear();

                FileManager.save(data);
            }
            catch (Exception ex)
            {
                showAlert("Please enter valid numbers for servings, calories, protein, carbs, and fat.");
            }
        });

        deleteButton.setOnAction(e ->
        {
            FoodEntry selected = listView.getSelectionModel().getSelectedItem();

            if (selected == null)
            {
                showAlert("Please select an entry to delete.");
                return;
            }

            data.removeEntry(selected);
            observableEntries.remove(selected);
            updateTotals();
            FileManager.save(data);
        });

        saveButton.setOnAction(e ->
        {
            FileManager.save(data);
            showAlert("Data saved successfully.");
        });

        BorderPane root = new BorderPane();
        root.setCenter(rootContent);

        Scene scene = new Scene(root, 1100, 650);

        stage.setTitle("Macro Tracker");
        stage.setScene(scene);
        stage.show();
    }

    private TextField createTextField(String prompt)
    {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setPrefHeight(42);
        textField.setStyle(
                "-fx-background-color: #f9fafb;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #d1d5db;" +
                        "-fx-border-radius: 12;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 0 12 0 12;"
        );
        return textField;
    }

    private Button createPrimaryButton(String text)
    {
        Button button = new Button(text);
        button.setPrefHeight(40);
        button.setStyle(
                "-fx-background-color: #111827;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0 16 0 16;"
        );
        return button;
    }

    private Button createSecondaryButton(String text)
    {
        Button button = new Button(text);
        button.setPrefHeight(40);
        button.setStyle(
                "-fx-background-color: #e5e7eb;" +
                        "-fx-text-fill: #111827;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0 16 0 16;"
        );
        return button;
    }

    private Label createMetricLabel()
    {
        Label label = new Label("0");
        label.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #111827;"
        );
        return label;
    }

    private VBox createStatCard(String title, Label valueLabel)
    {
        Label titleLabel = new Label(title);
        titleLabel.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #6b7280;" +
                        "-fx-font-weight: bold;"
        );

        VBox card = new VBox(8, titleLabel, valueLabel);
        card.setPadding(new Insets(16));
        card.setStyle(
                "-fx-background-color: #f9fafb;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-color: #e5e7eb;" +
                        "-fx-border-radius: 16;"
        );
        card.setPrefWidth(180);
        return card;
    }

    private void updateTotals()
    {
        caloriesLabel.setText(String.format("%.1f cal", data.getTotalCalories()));
        proteinLabel.setText(String.format("%.1f g", data.getTotalProtein()));
        carbsLabel.setText(String.format("%.1f g", data.getTotalCarbs()));
        fatLabel.setText(String.format("%.1f g", data.getTotalFat()));
        entryCountLabel.setText(observableEntries.size() + " item(s)");
    }

    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
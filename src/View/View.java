package View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.net.URL;

public class View extends Application {
    private Stage stage;
    private StackPane stackPane;
    private Map<String, Node> loadedFXMLs;

    @FXML
    private Button backButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea textArea;
    @FXML
    private Button saveButton;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private TextArea resultTextArea;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private View mainView;
    // @FXML
    // private BottomNavigationButton homeButton;
    // @FXML
    // private BottomNavigationButton personButton;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Text otherInfoText;
    @FXML
    private Text nameText;
    @FXML
    private Button editProfileButton;
    @FXML
    private DatePicker datePickerProfile;
    @FXML
    private Button addEventButton;
    @FXML
    private TextArea eventTextArea;
    @FXML
    private Text profileTitleText;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField confirmPasswordField;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        stackPane = new StackPane();
        loadedFXMLs = new HashMap<>();
        loadFXML("LoginScreen.fxml");

        Scene scene = new Scene(stackPane, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    // Method to load FXML file into the stackPane
    private void loadFXML(String fxmlFileName) {
        try {
            URL resourceUrl = getClass().getResource(fxmlFileName);
            if (resourceUrl == null) {
                System.err.println("FXML file not found: " + fxmlFileName);
                return;
            }
            System.out.println("Loading FXML file: " + resourceUrl);

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            if (root == null) {
                System.err.println("Root node is null for FXML file: " + fxmlFileName);
                return;
            }

            loadedFXMLs.put(fxmlFileName, root);
            stackPane.getChildren().add(root);
            root.setVisible(true); // Hide the loaded node initially
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlFileName);
            e.printStackTrace();
        }
    }

    // Method to show a specific loaded FXML file
    public void showFXML(String fxmlFileName) {
        Node node = loadedFXMLs.get(fxmlFileName);
        if (node != null) {
            node.setVisible(true);
            stackPane.getChildren().removeIf(n -> !n.equals(node));
        } else {
            loadFXML(fxmlFileName);
            showFXML(fxmlFileName);
        }
    }

    public Map<String, Node> getLoadedFXMLs() {
        return loadedFXMLs;
    }

    public Button getBackButton() {
        return backButton;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public TextArea getResultTextArea() {
        return resultTextArea;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public View getMainView() {
        return mainView;
    }

    public ImageView getProfileImageView() {
        return profileImageView;
    }

    public Text getOtherInfoText() {
        return otherInfoText;
    }

    public Text getNameText() {
        return nameText;
    }

    public Button getEditProfileButton() {
        return editProfileButton;
    }

    public DatePicker getDatePickerProfile() {
        return datePickerProfile;
    }

    public Button getAddEventButton() {
        return addEventButton;
    }

    public TextArea getEventTextArea() {
        return eventTextArea;
    }

    public Text getProfileTitleText() {
        return profileTitleText;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getSurnameField() {
        return surnameField;
    }

    public TextField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

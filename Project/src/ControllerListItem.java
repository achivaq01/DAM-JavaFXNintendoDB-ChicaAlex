import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class ControllerListItem {
    @FXML
    private ImageView img;

    @FXML
    private Label text = new Label();

    public void setText(String text) {
        // Estableix el contingut del Label
        this.text.setText(text);
    }

    public void setImage(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourceName);
        if (inputStream != null) {
            Image image = new Image(inputStream);
            img.setImage(image);
        } else {
            System.err.println("Failed to load image: " + resourceName);
        }
    }
}


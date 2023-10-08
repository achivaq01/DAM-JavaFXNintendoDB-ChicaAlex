import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ControllerInfoJoc {

    @FXML
    private ImageView img;

    @FXML
    private Label title = new Label();

    @FXML
    private Label text = new Label();

    @FXML
    private Label date = new Label();

    @FXML
    private Label type = new Label();

    public void setImgage(String resourceName){
        ClassLoader classLoader = getClass().getClassLoader();
        Image image = new Image(Objects.requireNonNull(classLoader.getResourceAsStream(resourceName)));

        img.setImage(image);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setText(String text){
        this.text.setText(text);
    }

    public void setDate(String date){
        this.date.setText(date);
    }

    public void setType(String type){
        this.type.setText(type);
    }
}

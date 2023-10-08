import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Objects;

public class ControllerInfoPersonatge {
    @FXML
    private ImageView img;

    @FXML
    private Label title = new Label();

    @FXML
    private Rectangle color = new Rectangle();

    @FXML
    private Label text = new Label();

    public void setImage(String resourceName){
        ClassLoader classLoader = getClass().getClassLoader();
        Image image = new Image(Objects.requireNonNull(classLoader.getResourceAsStream(resourceName)));

        img.setImage(image);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setColor(String color){
        Color paint = switch (color) {
            case "green" -> Color.GREEN;
            case "red" -> Color.RED;
            case "pink" -> Color.PINK;
            case "orange" -> Color.ORANGE;
            case "brown" -> Color.BROWN;
            case "yellow" -> Color.YELLOW;
            case "grey" -> Color.GREY;
            default -> Color.RED;
        };

        this.color.setFill(paint);
    }

    public void setText(String text){
        this.text.setText(text);
    }
}

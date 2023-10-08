import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Objects;

public class ControllerInfoConsola {
    @FXML
    private ImageView img;

    @FXML
    private Label title = new Label();

    @FXML
    private Rectangle color = new Rectangle();

    @FXML
    private Label date = new Label();

    @FXML
    private Label processor = new Label();

    @FXML
    private Label sales = new Label();

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
            case "purple" -> Color.PURPLE;
            case "white" -> Color.WHITE;
            case "black" -> Color.BLACK;
            case "grey" -> Color.GREY;
            default -> Color.BLACK;
        };

        this.color.setFill(paint);
    }

    public void setDate(String date){
        this.date.setText(date);
    }

    public void setProcessor(String processor){
        this.processor.setText(processor);
    }

    public void setSales(String sales){
        this.sales.setText(sales + " unitats venudes");
    }
}

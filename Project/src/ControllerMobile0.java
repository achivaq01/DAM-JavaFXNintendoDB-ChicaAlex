import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMobile0 implements Initializable {
    String[] opcions = { "Personatges", "Jocs", "Consoles" };

    @FXML
    private ListView<Label> list = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label personatges = new Label(opcions[0]);
        Label jocs = new Label(opcions[1]);
        Label consoles = new Label(opcions[2]);

        list.getItems().add(personatges);
        list.getItems().add(jocs);
        list.getItems().add(consoles);



    }


}

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.json.JSONArray;
import org.json.JSONObject;


public class ControllerDesktop implements Initializable{

    String[] opcions = { "Personatges", "Jocs", "Consoles" };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
// Afegeix les opcions al ChoiceBox
        choiceBox.getItems().addAll(opcions);
// Selecciona la primera opció
        choiceBox.setValue(opcions[0]);
// Callback que s’executa quan l’usuari escull una opció
        choiceBox.setOnAction((event) -> { loadList(); });
// Carregar automàticament les dades de ‘Personatges’
        loadList();
    }
    public void loadList() {
        // Obtenir l'opció seleccionada
        String opcio = choiceBox.getValue();

        // Obtenir una referència a AppData que gestiona les dades
        AppData appData = AppData.getInstance();

        // Mostrar el missatge de càrrega
        showLoading();

        // Demanar les dades
        appData.load(opcio, (result) -> {
            if (result == null) {
                System.out.println("ControllerDesktop: Error loading data.");
            } else {
                // Cal afegir el try/catch a la crida de ‘showList’
                try {
                    showList();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("ControllerDesktop: Error showing list.");
                }
            }
        });


    }

    public void showLoading() {
        yPane.getChildren().clear();

        ProgressIndicator progressIndicator = new ProgressIndicator();
        yPane.getChildren().add(progressIndicator);
    }


    public void showList() throws Exception {

        String opcioSeleccionada = choiceBox.getValue();

        AppData appData = AppData.getInstance();

        JSONArray dades = appData.getData(opcioSeleccionada);
        URL resource = this.getClass().getResource("/assets/template_list_item.fxml");

        yPane.getChildren().clear();

        // Carregar la llista amb les dades
        for (int i = 0; i < dades.length(); i++) {
            JSONObject consoleObject = dades.getJSONObject(i);
            if (consoleObject.has("nom")) {
                String nom = consoleObject.getString("nom");
                String imatge = "assets/images/" + consoleObject.getString("imatge");
                FXMLLoader loader = new FXMLLoader(resource);
                Parent itemTemplate = loader.load();
                ControllerListItem itemController = loader.getController();
                itemController.setText(nom);
                itemController.setImage(imatge);

                final String type = opcioSeleccionada;
                final int index = i;
                itemTemplate.setOnMouseClicked(event -> {
                    showInfo(type, index);
                });


                yPane.getChildren().add(itemTemplate);

            }
        }
    }

    private void showInfo(String type, int index) {
        AppData appData = AppData.getInstance();
        JSONObject dades = appData.getItemData(type, index);
        URL resource = null;

        switch (type) {
            case "Consoles":
                resource = this.getClass().getResource("assets/template_info_consola.fxml");
                break;
            case "Jocs":
                resource = this.getClass().getResource("assets/template_info_joc.fxml");
                break;
            case "Personatges":
                resource = this.getClass().getResource("assets/template_info_personatge.fxml");
                break;
        }

        if (resource == null) {
            System.out.println("ControllerDesktop: Error showing info. Could not find template file for type " + type);
            return;
        }

        info.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();

            switch (type) {
                case "Consoles" -> {
                    ControllerInfoConsola consoleController = loader.getController();
                    consoleController.setImage("assets/images/" + dades.getString("imatge"));
                    consoleController.setTitle(dades.getString("nom"));
                    consoleController.setProcessor(dades.getString("procesador"));
                    consoleController.setColor(dades.getString("color"));
                    consoleController.setDate(dades.getString("data"));
                    consoleController.setSales(Integer.toString(dades.getInt("venudes")));
                }
                case "Jocs" -> {
                    ControllerInfoJoc jocController = loader.getController();
                    jocController.setImgage("assets/images/" + dades.getString("imatge"));
                    jocController.setTitle(dades.getString("nom"));
                    jocController.setDate(Integer.toString(dades.getInt("any")));
                    jocController.setText(dades.getString("descripcio"));
                    jocController.setType(dades.getString("tipus"));
                }
                case "Personatges" -> {
                    ControllerInfoPersonatge personatgeController = loader.getController();
                    personatgeController.setImage("assets/images/" + dades.getString("imatge"));
                    personatgeController.setTitle(dades.getString("nom"));
                    personatgeController.setColor(dades.getString("color"));
                    personatgeController.setText(dades.getString("nom_del_videojoc"));
                }
            }

            info.getChildren().add(itemTemplate);
            AnchorPane.setTopAnchor(itemTemplate, 0.0);
            AnchorPane.setRightAnchor(itemTemplate, 0.0);
            AnchorPane.setBottomAnchor(itemTemplate, 0.0);
            AnchorPane.setLeftAnchor(itemTemplate, 0.0);
        } catch (Exception e) {
            System.out.println("ControllerDesktop: Error showing info.");
            e.printStackTrace();
        }
    }


        @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private VBox yPane;

    @FXML
    private AnchorPane info;
}

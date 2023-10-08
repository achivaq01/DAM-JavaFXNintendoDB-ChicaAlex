import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMobile0 implements Initializable {
    @FXML
    private Button personatges = new Button();

    @FXML
    private Button jocs = new Button();

    @FXML
    private Button consoles = new Button();

    @FXML
    private AnchorPane info;

    private String option;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void personatges(javafx.event.ActionEvent actionEvent){
        option = "Personatges";
        loadList();
        UtilsViews.setView("Mobile1");
    }

    public void jocs(javafx.event.ActionEvent actionEvent){
        option = "Jocs";
        loadList();
        UtilsViews.setView("Mobile1");
    }

    public void consoles(javafx.event.ActionEvent actionEvent){
        option = "Consoles";
        loadList();
        UtilsViews.setView("Mobile1");
    }


    public void goBack(ActionEvent actionEvent) {
        UtilsViews.setView("Mobile0");
    }

    public void loadList() {
        AppData appData = AppData.getInstance();

        // Demanar les dades
        appData.load(option, (result) -> {
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

    public void showList() throws Exception {
        AppData appData = AppData.getInstance();

        JSONArray dades = appData.getData(option);
        URL resource = this.getClass().getResource("/assets/layout_mobile_1.fxml");

        // Carregar la llista amb les dades
        for (int i = 0; i < dades.length(); i++) {
            JSONObject consoleObject = dades.getJSONObject(i);
            if (consoleObject.has("nom")) {
                String nom = consoleObject.getString("nom");
                String imatge = "assets/images/" + consoleObject.getString("imatge");
                FXMLLoader loader = new FXMLLoader(resource);
                Parent itemTemplate = loader.load();

                final String type = option;
                final int index = i;
                itemTemplate.setOnMouseClicked(event -> {
                    showInfo(type, index);
                });

            }
        }
    }

    private void showInfo(String type, int index) {
        AppData appData = AppData.getInstance();
        JSONObject dades = appData.getItemData(type, index);
        URL resource = switch (type) {
            case "Consoles" -> this.getClass().getResource("assets/template_info_consola.fxml");
            case "Jocs" -> this.getClass().getResource("assets/template_info_joc.fxml");
            case "Personatges" -> this.getClass().getResource("assets/template_info_personatge.fxml");
            default -> null;
        };

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

}

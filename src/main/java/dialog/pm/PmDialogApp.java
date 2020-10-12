package dialog.pm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * @author Chen Jiongyu
 */
public class PmDialogApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        System.setProperty("prism.lcdtext", "false");
        Dialog<String> dialog = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader();
        DialogPane dialogPane = fxmlLoader.load(getClass().getResource("PmDialog.fxml").openStream());
        PmDialog pmDialog = fxmlLoader.getController();
        pmDialog.addPinProject(new PmDialog.Project(new Image("images/google.png"), "Google", "images/google.png",
                "2020/10/08", "2020/10/08"));
        pmDialog.addPinProject(new PmDialog.Project(new Image("images/France.png"), "France", "images/France.png",
                "2020/10/08", "2020/10/08"));
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("项目管理");
        dialog.setResizable(true);
        dialog.show();
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                primaryStage.close();
                dialog.close();
            }
        });
    }
}

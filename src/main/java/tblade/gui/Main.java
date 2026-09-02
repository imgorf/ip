package tblade.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tblade.TBlade;

/**
 * A GUI for TBlade using FXML.
 */
public class Main extends Application {
    private static final String DATA_FILE_PATH = "data/duke.txt";

    private final TBlade tblade = new TBlade(DATA_FILE_PATH);

    /**
     * Loads MainWindow.fxml, injects the TBlade instance into its controller, and shows the stage.
     *
     * @param stage the primary stage provided by JavaFX
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("TBlade");
            fxmlLoader.<MainWindow>getController().setTblade(tblade);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Rafał Mikołajun
 */
public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        primaryStage.setTitle("Gra w życie");
        Scene scene = new Scene(root, 1024, 800);
        scene.getStylesheets().add("style/style.css");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(640);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

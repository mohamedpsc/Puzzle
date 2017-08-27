package sample;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        GridPane root = new GridPane();
        StackPane root = new StackPane();
        PuzzlePanel game = new PuzzlePanel(6);
        primaryStage.setTitle("Hello World");
        SwingNode test = new SwingNode();
        test.setContent(game);
        root.getChildren().add(test);
        test.resize(500, 500);
        Scene gameScene = new Scene(root, 1000, 1000);
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!game.isFinished()){
                    switch(event.getCode()) {
                        case UP:
                            game.moveUp();
                            break;
                        case DOWN:
                            game.moveDown();
                            break;
                        case LEFT:
                            game.moveLeft();
                            break;
                        case RIGHT :
                            game.moveRight();
                            break;
                        case W:
                            // TODO Move Up for 2nd Player.
                            break;
                        case S:
                            // TODO Move Down for 2nd Player
                            break;
                        case A:
                            // TODO Move Left for 2nd Player
                            break;
                        case D:
                            // TODO Move Right for 2nd Player
                            break;
                    }
                }
            }
        });
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
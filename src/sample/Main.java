package sample;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane root = new StackPane();
        PuzzlePanel game = new PuzzlePanel(4);
        primaryStage.setTitle("Hello World");
        SwingNode test = new SwingNode();
        test.setContent(game);
        root.getChildren().add(test);
        test.resize(500, 500);
        Scene gameScene = new Scene(root, 1000, 1000);
        gameScene.setOnKeyPressed(e->{
            System.out.println(e.getCode());
            switch (e.getCode()){
                case A:
                    game.moveLeft(false);
                    break;
                case W:
                    game.moveUp(false);
                    break;
                case D:
                    game.moveRight(false);
                    break;
                case S:
                    game.moveDown(false);
                    break;
                case Z:
                    if(e.isControlDown() && e.isShiftDown())
                        game.redo();
                    else if(e.isControlDown())
                        game.undo();
                    break;
            }
        });
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
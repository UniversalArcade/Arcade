/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxgamecenter;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import GameProcessor.GameModel;


public class FXGameCenter extends Application {
    
    private int aniDuration;
    private Pane imagePane;
    private Group imageGroup, bgEffectsGroup;
    
    private Scene scene;
    private Background bg;
    private ImageSlider imageSlider;
    private GameModel gameModel;
    
    @Override
    public void start(Stage primaryStage) {
        
        gameModel = new GameModel();
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        aniDuration = 500;
        
        imageGroup = new Group();
        imagePane = new Pane();
        
        bgEffectsGroup = new Group();
        imagePane.getChildren().add( bgEffectsGroup );
        imagePane.getChildren().add( imageGroup );
        
        scene = new Scene(imagePane, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        imagePane.setStyle("-fx-background-color: #000000;");
        primaryStage.setTitle("Newschool Arcade");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);

        bg = new Background(scene, bgEffectsGroup, aniDuration);
        
        Thread backgroundThread = new Thread( bg );
        backgroundThread.setDaemon(true);
        backgroundThread.start();
        
        imageSlider = new ImageSlider(scene, imageGroup, aniDuration);
        imageSlider.setGameIds( gameModel.getAllGameIDs() );
        
        Thread imageSliderThread = new Thread (imageSlider);
        imageSliderThread.setDaemon(true);
        imageSliderThread.start();
        
        imageSlider.addObserver( bg );

        primaryStage.show();
        
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

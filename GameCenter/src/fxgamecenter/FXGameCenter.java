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
import helper.OutPipe;
import GameProcessor.CheckNewGamesRunnable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FXGameCenter extends Application{
    
    private int aniDuration;
    private Pane imagePane;
    private Group imageGroup, bgEffectsGroup;
    
    private Scene scene;
    private Background bg;
    private ImageSlider imageSlider;
    private GameModel gameModel;
    
    
    @Override
    public void start(Stage primaryStage) {
  
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

//    @Override
//    public void update(Observable o, Object arg) {
//       
//    }
    
}

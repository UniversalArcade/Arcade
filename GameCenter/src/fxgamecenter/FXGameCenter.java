/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxgamecenter;


import com.sun.javafx.scene.traversal.Direction;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;



public class FXGameCenter extends Application {
    
    
    
    private boolean enterPressed;
    private int aniDuration;
    private Pane imagePane;
    private Group imageGroup, bgEffectsGroup;
    
    private Scene scene;
    private Background bg;
    private ImageSlider imageSlider;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        aniDuration = 500;
        
        imageGroup = new Group();
        imagePane = new Pane();
        
        bgEffectsGroup = new Group();
        imagePane.getChildren().add(bgEffectsGroup);
        imagePane.getChildren().add(imageGroup);
        
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
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent key) {
                
                if(imageSlider.getMoveAnimationStatus() == Animation.Status.STOPPED){
                    if(key.getCode() == KeyCode.D){
                        imageSlider.updateTransition( -1 );
                        bg.triggerBackgroundMoveAnimation( -1 );
                        
                    }
                    else if(key.getCode() == KeyCode.A){
                        imageSlider.updateTransition( 1 );
                        bg.triggerBackgroundMoveAnimation( 1 );
                    }
                    
                    else if(key.getCode() == KeyCode.ENTER){
                        if(!enterPressed){
                            //System.out.println("ID : " + ids.get( (int)(ids.size()/2) ));
                            //imageSlider.onKeyEnter();
                            enterPressed = true;
                            
                        }
                    }
                }
            }
        });
        
        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){

             @Override
             public void handle(KeyEvent key) {
                if(key.getCode() == KeyCode.ENTER){
                    enterPressed = false;
                } 
             }
        });
        
        
        
        primaryStage.show();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

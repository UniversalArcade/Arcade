package fxgamecenter;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;

public class FXGameCenter extends Application implements Observer{
    
    private int aniDuration;
    private Pane imagePane;
    private Group imageGroup, bgEffectsGroup, textInfoGroup, gameTitleGroup;
    
    private Scene scene;
    private Background bg;
    private ImageSlider imageSlider;
    private Thread backgroundThread;
    private Stage primaryStage;
   
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        aniDuration = 500;
        
        imageGroup = new Group();
        textInfoGroup = new Group();
        gameTitleGroup = new Group();
        
        imagePane = new Pane();
        
        bgEffectsGroup = new Group();
        imagePane.getChildren().add( bgEffectsGroup );
        imagePane.getChildren().add( imageGroup );
        imagePane.getChildren().add( textInfoGroup );
        imagePane.getChildren().add( gameTitleGroup );
        
        scene = new Scene(imagePane, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        imagePane.setStyle("-fx-background-color: #000000;");
        primaryStage.setTitle("Newschool Arcade");
        primaryStage.setScene(scene);        
        
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);        
        primaryStage.setFullScreen(true); 
        Platform.setImplicitExit(false); // prevents App from exiting when form is hiding
     
        
        
        bg = new Background(scene, bgEffectsGroup, aniDuration);
        
        backgroundThread = new Thread( bg );
        backgroundThread.setDaemon(true);
        backgroundThread.start();
        imageSlider = new ImageSlider(scene, imageGroup, textInfoGroup, gameTitleGroup, aniDuration);
        
        imageSlider.addObserver(this);
        
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

    @Override
    public void update(Observable o, Object arg) {
               
        int todo = (int)arg;
        
        switch(todo){            
            case 3: //reset                
                Platform.runLater(new Runnable(){
                    
                    @Override
                    public void run() {                        
                        primaryStage.show();
                        primaryStage.setFullScreen(true);
                        primaryStage.requestFocus();
                    }
                });
                break;
            case 4: //reset                
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        primaryStage.hide();
                    }
                });
                break;
        }        
    }
}

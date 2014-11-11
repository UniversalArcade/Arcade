/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxgamecenter;

import java.util.ArrayList;
import java.util.LinkedList;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


public class FXGameCenter extends Application {
    
    private LinkedList<ImageView> images;
    private LinkedList<Integer> ids;
    private boolean enterPressed;
    private double imgSizeX;
    private int imgThresh, imagesVisible, direction, moveAniDuration;
    private ParallelTransition moveImagesTransition;
    private Pane root;
    private Timeline moveImagesTimeline;
    
    
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        
        moveImagesTimeline = new Timeline();
        root = new Pane();
        scene = new Scene(root, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        root.setStyle("-fx-background-color: #000000;");
        primaryStage.setTitle("Newschool Arcade");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);

        imagesVisible = 5;
        moveAniDuration = 500;
        enterPressed = false; 
        direction = 0;
        imgThresh = 20;
        imgSizeX = (root.getWidth() - (imagesVisible-1) * imgThresh) / imagesVisible;
        images = new LinkedList();
        ids = new LinkedList();
        /*
        for(int i=5; i>0; i--){
            ids.add(i);
        }
        */
        ids.add(7);
        ids.add(6);
        ids.add(5);
        ids.add(4);
        ids.add(3);
        ids.add(2);
        ids.add(1);
        
        
        //make shure there are enough images to display, if not: fill array with already existing ids
        this.prepareStartUpImages();

        moveImagesTransition = new ParallelTransition();
        moveImagesTransition.setOnFinished(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {

                updateElements( direction );
                
                /*
                Bounds pos = images.get(images.size() -1).localToScene(images.get(images.size() -1).getBoundsInLocal());
                System.out.println(pos);
                System.out.println(images.get(images.size() -1).getTranslateX()); 
                */
            }
        });
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent key) {
                
                if(moveImagesTransition.getStatus() == Animation.Status.STOPPED){
                    if(key.getCode() == KeyCode.D){
                        direction = -1;
                        updateTransition( direction );
                        moveImagesTransition.play();
                    }
                    else if(key.getCode() == KeyCode.A){
                        direction = 1;
                        updateTransition( direction );
                        moveImagesTransition.play();
                    }
                    else if(key.getCode() == KeyCode.ENTER){
                        if(!enterPressed){
                            System.out.println("ID : " + ids.get( (int)(ids.size()/2) ));
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
        
        /*
        Rectangle rect =new Rectangle(images.getFirst().getFitWidth() + 20,images.getFirst().getFitHeight() + 20);        
        rect.relocate(50, 50);
        rect.setFill(Color.web("f2f2f2"));
        */
        
        //Outer Glow Effect
        
        
        
        //outerglow.setWidth(images.getFirst().getFitWidth() + 20);
        //outerglow.setHeight(images.getFirst().getFitHeight() + 20);
        
        
        
        //rect.setEffect(outerglow);
        //root.getChildren().add(rect);
        //images.getFirst().setEffect(outerglow);
        
        primaryStage.show();
    }
    
    
    
    public void prepareStartUpImages(){
        
        
        int toAdd = imagesVisible + 2 - ids.size();
        
        for(int i=0; i < toAdd; i++){
            ids.add(ids.get(0));
        }
        
        LinkedList<Integer> tmp = new LinkedList();
        for(int i=1; i<ids.size() + 1; i++){
            if(i == 1){
                tmp.add(ids.get(0));
            }
            else{
                if(i % 2 == 0){
                    tmp.addFirst(ids.get(i - 1 ));
                }
                else{
                    tmp.addLast(ids.get(i - 1));
                }
            }
        }
        
        for(int i=0; i < (imagesVisible + 2); i++){
            
            ImageView imageView = loadImageFromID(ids.get(i));
            root.getChildren().add(imageView);
            int c = images.size();
            System.out.println(c);
            
            //if current image is the first one in array 
            if(c == 0){
                imageView.toFront();
                imageView.setScaleX(1.2);
                imageView.setScaleY(1.2);
                //ImageOuterGlowEffect effect = new ImageOuterGlowEffect();
                imageView.setEffect( (new ImageOuterGlowEffect()).getEffect() );
                imageView.setX(scene.getWidth()/2 - imageView.getFitWidth()/2);
                images.add(imageView);
            }
            //if there are more then one image in the array
            else{
                if( c % 2 == 0 ){
                    imageView.setX(images.getLast().getX() + imageView.getFitWidth() + imgThresh);
                    images.add(imageView);                    
                }
                else{
                    imageView.setX(images.getFirst().getX() - imageView.getFitWidth() - imgThresh);
                    images.addFirst(imageView);
                }
                imageView.toBack();
            }
            //imageView.setEffect(outerglow);
            
        }
        ids = tmp;
        tmp = null;
    }
    
    public ImageView loadImageFromID(int id){
       //ImageView imageView = new ImageView( new Image("file:C:\\Users\\Public\\Arcade\\testpics\\" + id + ".jpg"));
       ImageView imageView = new ImageView( new Image("file:pics/" + id + ".jpg"));
       imageView.setFitHeight(300);
       imageView.setFitWidth(imgSizeX);
       imageView.setY(100);
       
       return imageView;
    }
    /*
    public void updateTransition(int direction){
            moveImagesTransition.getChildren().clear();
            //transitions = new ArrayList();
            
            double setToX;
            ScaleTransition st;
            ImageView nextCenter;
           
            if(direction > 0){
                setToX = imgSizeX + imgThresh;
                FadeTransition ftLeft = new FadeTransition(Duration.millis(moveAniDuration), images.getFirst());
                FadeTransition ftRight = new FadeTransition(Duration.millis(moveAniDuration), images.get(images.size()-2));
                ftLeft.setFromValue(0.0f);
                ftLeft.setToValue(1.0f);
                ftRight.setFromValue(1.0f);
                ftRight.setToValue(0.0f);
                moveImagesTransition.getChildren().add(ftLeft);
                moveImagesTransition.getChildren().add(ftRight);
                
                nextCenter = images.get( (int)(images.size() / 2) -1);
            }
            else{
                setToX = imgSizeX * -1 + imgThresh * -1;
                FadeTransition ftLeft = new FadeTransition(Duration.millis(moveAniDuration), images.get(1));
                FadeTransition ftRight = new FadeTransition(Duration.millis(moveAniDuration), images.getLast());
                ftLeft.setFromValue(1.0f);
                ftLeft.setToValue(0.0f);
                ftRight.setFromValue(0.0f);
                ftRight.setToValue(1.0f);
                moveImagesTransition.getChildren().add(ftLeft);
                moveImagesTransition.getChildren().add(ftRight);
                
                nextCenter = images.get( (int)(images.size() / 2) +1);
            }
            
            nextCenter.toFront();
            st = new ScaleTransition(Duration.millis(moveAniDuration / 1.5), nextCenter);
            st.setToX(1.2f);
            st.setToY(1.2f);
            moveImagesTransition.getChildren().add(st);
            
            ImageView nowCenter = images.get( (int)(images.size() / 2));
            ScaleTransition stNow = new ScaleTransition(Duration.millis(moveAniDuration / 1.5), nowCenter);
            stNow.setToX(1.0f);
            stNow.setToY(1.0f);
            moveImagesTransition.getChildren().add(stNow);

            for(ImageView imageView : images){
                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(moveAniDuration), imageView);
                translateTransition.setFromX(0);
                translateTransition.setToX(setToX);
                translateTransition.setInterpolator(Interpolator.EASE_BOTH);
                moveImagesTransition.getChildren().add(translateTransition);
            }

            //parallelTransition.getChildren().addAll(transitions);
    }
    */
    
    public void updateTransition(int direction){
            moveImagesTransition.getChildren().clear();
            
            moveImagesTimeline.getKeyFrames().clear();
            
            
            
            //transitions = new ArrayList();
            
            double setToX;
            ScaleTransition st;
            ImageView nextCenter;
           
            if(direction > 0){
                setToX = imgSizeX + imgThresh;
                FadeTransition ftLeft = new FadeTransition(Duration.millis(moveAniDuration), images.getFirst());
                FadeTransition ftRight = new FadeTransition(Duration.millis(moveAniDuration), images.get(images.size()-2));
                ftLeft.setFromValue(0.0f);
                ftLeft.setToValue(1.0f);
                ftRight.setFromValue(1.0f);
                ftRight.setToValue(0.0f);
                moveImagesTransition.getChildren().add(ftLeft);
                moveImagesTransition.getChildren().add(ftRight);
                
                nextCenter = images.get( (int)(images.size() / 2) -1);
            }
            else{
                setToX = imgSizeX * -1 + imgThresh * -1;
                FadeTransition ftLeft = new FadeTransition(Duration.millis(moveAniDuration), images.get(1));
                FadeTransition ftRight = new FadeTransition(Duration.millis(moveAniDuration), images.getLast());
                ftLeft.setFromValue(1.0f);
                ftLeft.setToValue(0.0f);
                ftRight.setFromValue(0.0f);
                ftRight.setToValue(1.0f);
                moveImagesTransition.getChildren().add(ftLeft);
                moveImagesTransition.getChildren().add(ftRight);
                
                nextCenter = images.get( (int)(images.size() / 2) +1);
            }
            
            nextCenter.toFront();
            nextCenter.setEffect( (new ImageOuterGlowEffect()).getEffect() );
            
            st = new ScaleTransition(Duration.millis(moveAniDuration / 1.5), nextCenter);
            st.setToX(1.2f);
            st.setToY(1.2f);
            moveImagesTransition.getChildren().add(st);
            
            ImageView nowCenter = images.get( (int)(images.size() / 2));
            //nowCenter.setEffect(null);
            ScaleTransition stNow = new ScaleTransition(Duration.millis(moveAniDuration / 1.5), nowCenter);
            stNow.setToX(1.0f);
            stNow.setToY(1.0f);
            moveImagesTransition.getChildren().add(stNow);

            
            for(ImageView imageView : images){
                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(moveAniDuration), imageView);
                translateTransition.setFromX(0);
                translateTransition.setToX(setToX);
                translateTransition.setInterpolator(Interpolator.EASE_BOTH);
                moveImagesTransition.getChildren().add(translateTransition);
            }
            
            
            DropShadow nextBlurEffect = (DropShadow)nextCenter.getEffect();
            DropShadow nowBlurEffect = (DropShadow)nowCenter.getEffect();
            
            
            moveImagesTimeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, // set start position at 0
                    //new KeyValue(outerglow.heightProperty(), 0)
                    new KeyValue(nextBlurEffect.heightProperty(), 0),
                    new KeyValue(nextBlurEffect.widthProperty(), 0),
                    new KeyValue(nowBlurEffect.heightProperty(), 20),
                    new KeyValue(nowBlurEffect.widthProperty(), 20)        
                ),
                new KeyFrame(new Duration(moveAniDuration), // set end position at 40s
                    new KeyValue(nextBlurEffect.heightProperty(), 20),
                    new KeyValue(nextBlurEffect.widthProperty(), 20),
                    new KeyValue(nowBlurEffect.heightProperty(), 0),
                    new KeyValue(nowBlurEffect.widthProperty(), 0)        
                )
            );
            
            moveImagesTransition.getChildren().add(moveImagesTimeline);
           
    }
    
    public void updateElements(int direction){

        for(ImageView imageView : images){
            imageView.setX(imageView.getX() + imageView.getTranslateX());
            imageView.setTranslateX(0);
        }
        
        if(direction > 0){
            ids.addFirst(ids.pollLast());
            images.pollLast();

            int index = (int)(ids.size()/2) - ((int)(imagesVisible/2 ) +1) ;

            ImageView newImage = loadImageFromID(ids.get( index ));
            newImage.setX(images.getFirst().getX() - newImage.getFitWidth() - imgThresh);
            newImage.setOpacity(0);
            //newImage.setEffect(outerglow);
            images.addFirst(newImage);
            root.getChildren().add(newImage);
        }
        else{
            ids.add(ids.pollFirst());
            images.pollFirst();
                
            int index = (int)(ids.size()/2) + ((int)(imagesVisible/2 ) +1) ;
                
            ImageView newImage = loadImageFromID(ids.get( index ));
            newImage.setX(images.getLast().getX() + newImage.getFitWidth() + imgThresh);
            newImage.setOpacity(0);
            //newImage.setEffect(outerglow);
            images.addLast(newImage);   
            root.getChildren().add(newImage);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

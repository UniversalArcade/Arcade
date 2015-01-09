
package fxgamecenter;

import GameProcessor.CheckNewGamesRunnable;
import GameProcessor.GameModel;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import javafx.util.Duration;

public class ImageSlider extends Observable implements Runnable, Observer{

    
    public enum State{
        SLIDER, DETAILS
    }
    
    private State state;
    private LinkedList<ImageView> images;
    private LinkedList<Integer> ids;
    private boolean enterPressed;
    private double imgSizeX, imgSizeY;
    private double[] origImageSize = {500.0,800.0};
    private int imgThresh, imagesVisible, moveAniDuration;
    private ParallelTransition moveImagesTransition;
    private FadeTransition fadeIncoming, fadeOutgoing;
    private TranslateTransition translateTransition, moveSliderGroupToTop;
    private ScaleTransition scaleBigTransition, scaleNormalTransition;
    private Timeline outerGlowAnimation;
    private Group imageGroup;
    private Scene scene;
    private GameModel gameModel;

    public ImageSlider(Scene scene, Group group, int moveAniDuration){
        this.scene = scene;
        this.imageGroup = group;
        this.moveAniDuration = moveAniDuration;
        this.gameModel = new GameModel();
        this.ids = new LinkedList();
        this.setGameIds();
        
        CheckNewGamesRunnable ght = new CheckNewGamesRunnable();
        ght.addObserver(this);
        
        
        Thread checkThread = new Thread(ght);
        checkThread.setDaemon(true);
        checkThread.start();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("OBSERVED !");
        LinkedList list = (LinkedList)arg;
        
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                setGameIds();
                init();
            }
        });
    }

    public void setGameIds(){
        ids = gameModel.getAllGameIDs();
    }
    
    @Override
    public void run(){
        init();
    }
    
    public void init(){
        state = State.SLIDER;
        imagesVisible = 5; // 5
        //moveAniDuration = 500;
        enterPressed = false; 
        imgThresh = 20;
        imgSizeX = (scene.getWidth() - (imagesVisible-1) * imgThresh) / imagesVisible;
        imgSizeY = (imgSizeX / origImageSize[0]) * origImageSize[1] ; // 350 bei 3
        images = new LinkedList();
        
        moveImagesTransition = new ParallelTransition();
        //make shure there are enough images to display, if not: fill array with already existing ids
        this.prepareStartUpImages();
        this.prepareTransition();
         
        moveImagesTransition.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                updateElements();
            }
        });
        
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent key) {
                
                if(getMoveAnimationStatus() == Animation.Status.STOPPED){
                    if(key.getCode() == KeyCode.D){
                        updateTransition( -1 );
                    }
                    else if(key.getCode() == KeyCode.A){
                        updateTransition( 1 );
                    }
                    else if(key.getCode() == KeyCode.ENTER){
                        if(!enterPressed){
                            //loadDetailsPage();
                            gameModel.executeGameByID( ids.get( (int)(ids.size()/2) ));
                            System.out.println("ID : " + ids.get( (int)(ids.size()/2) ));
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
        
    }
    
    private void loadDetailsPage(){
        moveSliderGroupToTop.play();
    }
    
    private void stateEngine(){
        if(state == State.DETAILS){
            updateTransition( 1 );
        }
    }
    
    public void prepareStartUpImages(){
        
        imageGroup.getChildren().clear();
        
        int toAdd = imagesVisible + 2 - ids.size();
        
        for(int i=0; i < toAdd; i++){
            ids.add( ids.get( 0 ) );
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
            
            ImageView imageView = new ImageView( loadImageFromID(ids.get(i) ) );
            imageView.setFitHeight(imgSizeY); // 500
            imageView.setFitWidth(imgSizeX);
            imageView.setCache(true);
            imageView.setCacheHint(CacheHint.SPEED);
            
            imageGroup.getChildren().add(imageView);
            int c = images.size();
            
            //if current image is the first one in array 
            if(c == 0){
                imageView.toFront();
                imageView.setScaleX(1.2);
                imageView.setScaleY(1.2);
                imageView.setEffect( (new ImageOuterGlowEffect(20,20)).getEffect() );
                imageView.setX( scene.getWidth() / 2 - imageView.getFitWidth() / 2 );
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
                imageView.setEffect( (new ImageOuterGlowEffect()).getEffect() );
                imageView.toBack();
            }            
        }
        imageGroup.setTranslateY(scene.getHeight() / 2 - images.getFirst().getFitHeight() / 2);
        
        ids = tmp;
        tmp = null;
    }
    
    public Image loadImageFromID(int id){
        //image, backgroundloading
        //return new Image("file:pics/G" + id + ".jpg",true);
        return new Image("file:C:\\Users\\Public\\Arcade\\Games\\" + id + "\\assets\\" + id + ".jpg",true);
        
    }
    
    public void prepareTransition(){
        
        moveSliderGroupToTop = new TranslateTransition(Duration.millis(200), imageGroup);
        moveSliderGroupToTop.setToY(30);   
        moveSliderGroupToTop.setInterpolator(Interpolator.EASE_BOTH);
        
        outerGlowAnimation = new Timeline();
        
        translateTransition = new TranslateTransition(Duration.millis(moveAniDuration), imageGroup);
        translateTransition.setFromX(0);   
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);

        fadeIncoming = new FadeTransition(Duration.millis(moveAniDuration));
        fadeIncoming.setFromValue(0.0f);
        fadeIncoming.setToValue(1.0f);

        fadeOutgoing = new FadeTransition(Duration.millis(moveAniDuration));
        fadeOutgoing.setFromValue(1.0f);
        fadeOutgoing.setToValue(0.0f);

        scaleBigTransition = new ScaleTransition(Duration.millis(moveAniDuration / 1.5));
        scaleBigTransition.setToX(1.2f);
        scaleBigTransition.setToY(1.2f);

        scaleNormalTransition = new ScaleTransition(Duration.millis(moveAniDuration / 1.5));
        scaleNormalTransition.setToX(1.0f);
        scaleNormalTransition.setToY(1.0f);
         
        moveImagesTransition.getChildren().addAll(
                translateTransition, 
                fadeIncoming, 
                fadeOutgoing, 
                scaleBigTransition, 
                scaleNormalTransition, 
                outerGlowAnimation
        );
    }
    
    public void updateTransition(int direction){
            
            setChanged(); //Observerable
            notifyObservers( direction );
            
            ImageView nextCenter;
           
            if(direction > 0){
                translateTransition.setToX( imgSizeX + imgThresh );
                
                fadeIncoming.setNode(images.getFirst());
                fadeOutgoing.setNode(images.get(images.size()-2));
                
                nextCenter = images.get( (int)(images.size() / 2) -1 );
            }
            else{
                translateTransition.setToX( imgSizeX * -1 + imgThresh * -1 );
                
                fadeIncoming.setNode(images.getLast());
                fadeOutgoing.setNode(images.get(1));
                
                nextCenter = images.get( (int)(images.size() / 2) +1);
            }

            nextCenter.toFront();
            
            scaleBigTransition.setNode(nextCenter);
            
            ImageView nowCenter = images.get( (int)(images.size() / 2));
            scaleNormalTransition.setNode(nowCenter);
            
            
            DropShadow nextBlurEffect = (DropShadow)nextCenter.getEffect();
            DropShadow nowBlurEffect = (DropShadow)nowCenter.getEffect();

            outerGlowAnimation.getKeyFrames().clear();
            outerGlowAnimation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, // set start position at 0
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
            moveImagesTransition.play();           
    }
    
    public Animation.Status getMoveAnimationStatus(){
        return moveImagesTransition.getStatus();
    }
    
    public void updateElements(){
        
        for(ImageView imageView : images){
            imageView.setX(imageView.getX() + imageGroup.getTranslateX());
        }

        ImageView newImage;
        
        if(imageGroup.getTranslateX() > 0){
            ids.addFirst( ids.pollLast() );
            int index = (int)(ids.size()/2) - ((int)(imagesVisible/2 ) +1) ;
            
            images.addFirst( images.pollLast() );
            newImage = images.getFirst();
            newImage.setImage( loadImageFromID( ids.get( index ) ) );
            newImage.setX( images.get(1).getX() - newImage.getFitWidth() - imgThresh );
        }
        else{
            ids.add( ids.pollFirst() );
            int index = (int)(ids.size()/2) + ((int)(imagesVisible/2 ) +1) ;
            
            images.add( images.pollFirst() );
            newImage = images.getLast();
            newImage.setImage( loadImageFromID(ids.get( index ) ) );
            newImage.setX( images.get( images.size() -2 ).getX() + newImage.getFitWidth() + imgThresh );
        }
        
        newImage.setOpacity(0);
        newImage.setCache(true);
        newImage.setCacheHint(CacheHint.SPEED);
        imageGroup.setTranslateX(0);
    }
    
    
    
    
}

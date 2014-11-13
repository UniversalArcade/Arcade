
package fxgamecenter;

import java.util.LinkedList;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.util.Duration;

public class ImageSlider extends Thread{
     private LinkedList<ImageView> images;
    private LinkedList<Integer> ids;
    private boolean enterPressed;
    private double imgSizeX;
    private int imgThresh, imagesVisible, moveAniDuration;
    private ParallelTransition moveImagesTransition;
    private FadeTransition fadeIncoming, fadeOutgoing;
    private TranslateTransition translateTransition;
    private ScaleTransition scaleBigTransition, scaleNormalTransition;
    
    private Timeline outerGlowAnimation;
    private Group imageGroup;
    
    private Scene scene;
    //private Background bg;
    
    
    public ImageSlider(Scene scene, Group group){
        setDaemon(true);
        this.scene = scene;
        this.imageGroup = group;
        //this.bg = bg;
        
    }
    
    @Override
    public void run(){
        init();
    }
    
    public void init(){
        
        imagesVisible = 5;
        moveAniDuration = 500;
        enterPressed = false; 
        imgThresh = 20;
        imgSizeX = (scene.getWidth() - (imagesVisible-1) * imgThresh) / imagesVisible;
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
            
            ImageView imageView = new ImageView( loadImageFromID(ids.get(i) ) );
            imageView.setFitHeight(300);
            imageView.setFitWidth(imgSizeX);
            imageView.setY( scene.getHeight() / 2 - imageView.getFitHeight() / 2 );
            
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
        ids = tmp;
        tmp = null;
    }
    
    public Image loadImageFromID(int id){
       // image, backgroundloading
       return new Image("file:pics/" + id + ".jpg", true);
    }
    
    public void prepareTransition(){
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

        if(imageGroup.getTranslateX() > 0){
            ids.addFirst( ids.pollLast() );
            int index = (int)(ids.size()/2) - ((int)(imagesVisible/2 ) +1) ;
            
            images.addFirst( images.pollLast() );
            ImageView newImage = images.getFirst();
            newImage.setImage( loadImageFromID( ids.get( index ) ) );
            newImage.setX( images.get(1).getX() - newImage.getFitWidth() - imgThresh );
            newImage.setOpacity(0);
        }
        else{
            ids.add( ids.pollFirst() );
            int index = (int)(ids.size()/2) + ((int)(imagesVisible/2 ) +1) ;
            
            images.add( images.pollFirst() );
            ImageView newImage = images.getLast();
            newImage.setImage( loadImageFromID(ids.get( index ) ) );
            newImage.setX( images.get( images.size() -2 ).getX() + newImage.getFitWidth() + imgThresh );
            newImage.setOpacity(0);
        }
        imageGroup.setTranslateX(0);
    }
    
    
    
    
}

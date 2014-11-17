/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxgamecenter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
public class Background extends Thread{
    
    private int radius, lastHorizontalDirection, parentAniduration;
    private double backgroundXTranslation;
    private Random rand;
    private Timeline[] moveBackgroundTimeline;
    private ParallelTransition MoveBGVertically;
    private Group circles;
    private Group group;
    private Scene scene;
    ArrayList<Group> circleGroups;
    
    public Background(Scene scene, Group group, int parentAniduration){
        radius = 30;
        rand = new Random();
        backgroundXTranslation = 0;
        moveBackgroundTimeline = new Timeline[2];
        MoveBGVertically = new ParallelTransition();
        circles = new Group();
        this.group = group;
        this.scene = scene;
        this.parentAniduration = parentAniduration;
        circleGroups = new ArrayList();
        setDaemon(true);
    }
    
    @Override 
    public void run(){
        init();
    }
    
    
    
    public void init(){

        for (int i = 0; i < 150; i++) {
            Circle circle = new Circle(rand.nextDouble() * radius + 10, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
            circle.relocate(rand.nextDouble() * scene.getWidth(), rand.nextDouble() * (scene.getHeight() + radius * 2));
        }
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.web("#f8bd55")),
                    new Stop(0.14, Color.web("#c0fe56")),
                    new Stop(0.28, Color.web("#5dfbc1")),
                    new Stop(0.43, Color.web("#64c2f8")),
                    new Stop(0.57, Color.web("#be4af7")),
                    new Stop(0.71, Color.web("#ed5fc2")),
                    new Stop(0.85, Color.web("#ef504c")),
                    new Stop(1, Color.web("#f2660f")),}));
        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());
        Group blendModeGroup =
                new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
                     Color.BLACK), circles), colors);
        colors.setBlendMode(BlendMode.OVERLAY);
        
        
        group.getChildren().add(blendModeGroup);      
        circles.setEffect(new BoxBlur(10, 10, 3));
        
        Timeline bubbleTimeline = new Timeline();
        bubbleTimeline.setCycleCount(Animation.INDEFINITE);
        
       
        for (Node circle : circles.getChildren()) { 
            KeyFrame moveBall = new KeyFrame(Duration.seconds(.0300), // .0300
                new EventHandler<ActionEvent>() {
                    
                    int counter = 0;
                    Bounds pos = circle.localToScene(circle.getBoundsInLocal());
                    double yMin = pos.getMinY();
                    double yMax = pos.getMaxY();
                    double dy = 0.4 * ( (yMax - yMin) / (radius*2 + 10) );
                    
                    public void handle(ActionEvent event) {
                        counter++;
                        if(counter > 50){
                            pos = circle.localToScene(circle.getBoundsInLocal());
                            yMin = pos.getMinY();
                            yMax = pos.getMaxY();
                            
                            if(yMax < 0){
                                circle.setTranslateY(0);
                                circle.relocate(rand.nextDouble() * scene.getWidth(),scene.getHeight() + (yMax - yMin));
                            }
                            
                            counter = 0;
                        }

                        circle.setTranslateY(circle.getTranslateY() - dy);   
                    }
                });
                bubbleTimeline.getKeyFrames().add(moveBall);
        }
        bubbleTimeline.play();
        
        moveBackgroundTimeline[0] = generateBackgroundMoveAnimation(-1);
        moveBackgroundTimeline[1] = generateBackgroundMoveAnimation(1);
    }
    
    public Timeline generateBackgroundMoveAnimation(int direction){
        Timeline tmp = new Timeline();
        
        tmp.setCycleCount(parentAniduration);
        for (Node circle : circles.getChildren()) {
            
            KeyFrame moveHorizontally = new KeyFrame(Duration.seconds(.0010), // .0300
                new EventHandler<ActionEvent>() {
                    
                    int counter = 0;
                    Bounds pos = circle.localToScene(circle.getBoundsInLocal());
                    double xMin = pos.getMinX();
                    double xMax = pos.getMaxX();
                    double dx = direction * -0.2 ;
                    
                    public void handle(ActionEvent event) {
                        counter++;
                        if(counter > 10){ //wip: test every loop
                            pos = circle.localToScene(circle.getBoundsInLocal());
                            xMin = pos.getMinX();
                            xMax = pos.getMaxX();
                                                        
                            if(xMin >= scene.getWidth()){
                                circle.setTranslateX(circle.getTranslateX() + scene.getWidth() * -1 - (xMax - xMin));
                            }
                            if(xMax <= 0){
                                circle.setTranslateX(circle.getTranslateX() + scene.getWidth() + (xMax - xMin));
                            }
                            
                            counter = 0;
                        }
                        circle.setTranslateX(circle.getTranslateX() + dx);
                    }
                });
                tmp.getKeyFrames().add(moveHorizontally);
        }
        return tmp;   
    } 

    public void triggerBackgroundMoveAnimation(int direction){
        if(direction > 0){
            moveBackgroundTimeline[1].play();
        }
        else{
            moveBackgroundTimeline[0].play();
        }
    }    
}

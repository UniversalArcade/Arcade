/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxgamecenter;

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
public class Background {
    
    private int radius, aniCounter;
    private double backgroundXTranslation;
    private Random rand;
    private Timeline moveBackgroundTimeline;
    private Group circles;
    private Group group;
    private Scene scene;
    private LinkedList<Circle> circlesList;
    
    public Background(Scene scene, Group group){
        radius = 30;
        rand = new Random();
        backgroundXTranslation = 0;
        moveBackgroundTimeline = new Timeline();
        circles = new Group();
        this.group = group;
        this.scene = scene;
        circlesList = new LinkedList();
    }
    
    
    
    public void init(){
        
        
        
        //Group circles = new Group();
        for (int i = 0; i < 300; i++) {
            Circle circle = new Circle(rand.nextDouble() * radius + 10, Color.web("white", 0.05));
            //Circle circle = new Circle(radius * 2, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circlesList.add(circle);
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
        
        aniCounter = 0;
        for (Node circle : circles.getChildren()) {
            aniCounter++;
            KeyFrame moveBall = new KeyFrame(Duration.seconds(.0300), // .0300
                new EventHandler<ActionEvent>() {
                    
                    int counter = aniCounter;
                    
                    public void handle(ActionEvent event) {
                        Bounds pos = circle.localToScene(circle.getBoundsInLocal());
                        //System.out.println(pos);
                        double xMin = pos.getMinX();
                        double yMin = pos.getMinY();
                        double xMax = pos.getMaxX();
                        double yMax = pos.getMaxY();
                        
                        //double dx = 0.5;
                        double dy = 0.4 * ( (xMax - xMin) / (radius*2 + 10)  ) + counter/400;
                        
                        /*
                        if(xMin >= scene.getWidth()){
                            //circle.setTranslateX(0);
                            Bounds newPos = circle.localToScene(circle.getBoundsInLocal());
                            //circle.setTranslateX(newPos.getMinX() - scene.getWidth() - 100);
                            //circle.setTranslateX(-400);
                            circle.setTranslateX(circle.getTranslateX() + -1920);
                            //circle.setTranslateX(xMin - scene.getWidth());
                            //circle.relocate(100,  rand.nextDouble() * scene.getHeight());
                        }
                        */
                        if(yMax < 0){
                            circle.setTranslateY(0);
                            circle.relocate(rand.nextDouble() * scene.getWidth(),scene.getHeight() + (xMax - xMin));
                        }
                        
                        //circle.setTranslateX(circle.getTranslateX() + dx);
                        circle.setTranslateY(circle.getTranslateY() - dy);   
                    }
                });
                bubbleTimeline.getKeyFrames().add(moveBall);
        }
        
        bubbleTimeline.play();
        
    }
    
    public void setBackgroundXTranslation(double trans){
        this.backgroundXTranslation = trans;
    }
    
    public Timeline getBackgroundMoveAnimation(){
        moveBackgroundTimeline.getKeyFrames().clear();
        
        /*
        for (Node circle : circles.getChildren()) {
            moveBackgroundTimeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                    new KeyValue(circle.translateXProperty(), circle.getTranslateX())),                    
                    new KeyFrame(new Duration(20500), // set end position at 40s
                    new KeyValue(circle.translateXProperty(), circle.getTranslateX() + scene.getWidth())));
        }
                */
        /*
        for (Node circle : circles.getChildren()) {
            moveBackgroundTimeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                    new KeyValue(circle.translateXProperty(), circle.getTranslateX())),                    
                    new KeyFrame(new Duration(1000), // set end position at 40s
                    new KeyValue(circle.translateXProperty(), circle.getTranslateX() + scene.getWidth())));
        }
        */
        
        
        //moveBackgroundTimeline.setCycleCount(Animation.INDEFINITE);
        moveBackgroundTimeline.setCycleCount(500);
        
        //aniCounter = 0;
        for (Node circle : circles.getChildren()) {
            //aniCounter++;
            KeyFrame moveBall = new KeyFrame(Duration.seconds(.0010), // .0300
                new EventHandler<ActionEvent>() {
                    
                    //int counter = aniCounter;
                    
                    public void handle(ActionEvent event) {
                        Bounds pos = circle.localToScene(circle.getBoundsInLocal());
                        //System.out.println(pos);
                        double xMin = pos.getMinX();
                        double xMax = pos.getMaxX();
                        
                        
                        double dx = 1;
                        //double dy = 0.4 * ( (xMax - xMin) / (radius*2 + 10)  ) + counter/400;
                        
                        
                        if(xMin >= scene.getWidth()){
                            //circle.setTranslateX(0);
                            //Bounds newPos = circle.localToScene(circle.getBoundsInLocal());
                            //circle.setTranslateX(newPos.getMinX() - scene.getWidth() - 100);
                            //circle.setTranslateX(-400);
                            circle.setTranslateX(circle.getTranslateX() + scene.getWidth() * -1 - (xMax - xMin));
                            //circle.setTranslateX(xMin - scene.getWidth());
                            //circle.relocate(100,  rand.nextDouble() * scene.getHeight());
                        }
                       
                        
                        circle.setTranslateX(circle.getTranslateX() + dx);
                        //circle.setTranslateY(circle.getTranslateY() - dy);   
                    }
                });
                moveBackgroundTimeline.getKeyFrames().add(moveBall);
        }
        
        
              
        
        
        
        
        return moveBackgroundTimeline;
        //return moveImagesTransition;
    }
    
    
    
}

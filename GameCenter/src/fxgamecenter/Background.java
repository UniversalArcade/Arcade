/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxgamecenter;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 *
 * @author Martin
 */
public class Background {
    
    private int radius;
    private Random rand;
    
    public void init(Scene scene, Group group){

        radius = 30;
        rand = new Random();
        
        Group circles = new Group();
        for (int i = 0; i < 300; i++) {
            Circle circle = new Circle(radius, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
            circle.relocate(rand.nextDouble() * scene.getWidth(), rand.nextDouble() * scene.getHeight());
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
       
        
        
        Timeline t1 = new Timeline();
        t1.setCycleCount(Animation.INDEFINITE);
        for (Node circle : circles.getChildren()) {
            KeyFrame moveBall = new KeyFrame(Duration.seconds(.0300),
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        
                        double dx = 0.5;
                        double dy = 0.4;
                        
                        Bounds pos = circle.localToScene(circle.getBoundsInLocal());
                        //System.out.println(pos);
                        double xMin = pos.getMinX();
                        double yMin = pos.getMinY();
                        double xMax = pos.getMaxX();
                        double yMax = pos.getMaxY();
                        
                        if(xMin > scene.getWidth()){
                            circle.setTranslateX(0);
                            circle.relocate(radius * -2 -5, rand.nextDouble() * scene.getHeight());
                        }
                        if(yMax < 0){
                            circle.setTranslateY(0);
                            circle.relocate(rand.nextDouble() * scene.getWidth(),scene.getHeight() + radius * 2);
                        }
                        
                        //circle.setTranslateX(circle.getTranslateX() + dx);
                        circle.setTranslateY(circle.getTranslateY() - dy);

                    }
                });

                t1.getKeyFrames().add(moveBall);
        }
        t1.play();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxgamecenter;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class ImageOuterGlowEffect {
    
    private int width, height;

    public ImageOuterGlowEffect(){
        this.width = 0;
        this.height = 0;
    }
    
    public ImageOuterGlowEffect(int width, int height){
        this.width = width;
        this.height = height;
    }

    public DropShadow getEffect(){
        DropShadow outerglow = new DropShadow();
        outerglow.setOffsetX(0);
        outerglow.setOffsetY(0);
        outerglow.setWidth(width); // 20
        outerglow.setHeight(height); // 20
        outerglow.setSpread(0.5);
        outerglow.setColor(Color.YELLOW);
        return outerglow;
    }
}

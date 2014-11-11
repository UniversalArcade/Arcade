/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxgamecenter;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class ImageOuterGlowEffect {
    
    public DropShadow getEffect(){
        DropShadow outerglow = new DropShadow();
        outerglow.setOffsetX(0);
        outerglow.setOffsetY(0);
        outerglow.setWidth(20);
        outerglow.setHeight(20);
        outerglow.setSpread(0.5);
        outerglow.setColor(Color.WHITE);
        return outerglow;
    }
}

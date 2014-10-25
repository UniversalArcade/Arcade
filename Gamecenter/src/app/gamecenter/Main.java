package app.gamecenter;

import app.server.nanoHTTPd.common.NanoHTTPD;
import app.server.nanoHTTPd.websocket.NanoWebSocketServer;
import app.server.nanoHTTPd.websocket.WebSocket;
import app.server.websocket.IWebSocket;
import app.server.nanoHTTPd.webserver.SimpleWebServer;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.geometry.VPos;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCombination;
import javafx.stage.WindowEvent;
 
 
public class Main extends Application {
    private Scene scene;
    
    
    public NanoWebSocketServer webSocketServer;
    public SimpleWebServer webServer;
    
    
    
    @Override 
    public void start(Stage stage) throws IOException {
        // create the scene

        webServer = new SimpleWebServer("127.0.0.1",8080,new File("src/Frontend"),false);
        webServer.startWebserver();

        webSocketServer = new NanoWebSocketServer(56400){
            @Override
            public WebSocket openWebSocket(NanoHTTPD.IHTTPSession handshake) {
                return new IWebSocket(handshake);
            }
        };
        try {
            webSocketServer.start();
        } catch (IOException ex) {}
        
        
        stage.setTitle("Arcade Frontend");
        scene = new Scene(new Browser(),750,500, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add("webviewsample/BrowserToolbar.css");        
        stage.setFullScreen(true);
        
        //supress info message to esc fullscreen with esc key
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                webServer.stopWebServer();
                webSocketServer.stop();
            }
        }); 
    }
 
    public static void main(String[] args){
        launch(args);
    }
}
class Browser extends Region {
 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    
     
    public Browser() {
        //apply the styles
        //getStyleClass().add("browser");
        // load the web page
        webEngine.load("http://localhost:8080/");
        //add the web view to the scene
        getChildren().add(browser);
 
    }

    @Override 
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override 
    protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override 
    protected double computePrefHeight(double width) {
        return 500;
    }
}
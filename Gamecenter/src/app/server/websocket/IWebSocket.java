package app.server.websocket;

import app.gamecenter.MessageController;
import app.server.nanoHTTPd.common.NanoHTTPD;
import app.server.nanoHTTPd.websocket.WebSocket;
import app.server.nanoHTTPd.websocket.WebSocketFrame;

import java.io.IOException;
import org.json.simple.JSONObject;

/**
* @author Paul S. Hawke (paul.hawke@gmail.com)
*         On: 4/23/14 at 10:34 PM
*/
public class IWebSocket extends WebSocket {


    public IWebSocket(NanoHTTPD.IHTTPSession handshake) {
        super(handshake);
    }

    @Override
    protected void onPong(WebSocketFrame pongFrame) {
       
    }

    @Override
    protected void onMessage(WebSocketFrame messageFrame) {
        try {
            System.out.println("onMessage: " + messageFrame.getTextPayload());
            
            MessageController msgCtrl = new MessageController();
            String confirm = msgCtrl.processMessage( messageFrame.getTextPayload() );
            
            messageFrame.setUnmasked();
            //sendFrame(messageFrame);

            if(!confirm.isEmpty()){
                send(confirm);
            }
            else{
                //debug
                send("empty");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onClose(WebSocketFrame.CloseCode code, String reason, boolean initiatedByRemote) { 
    }

    @Override
    protected void onException(IOException e) {
        System.out.println("onException");
        e.printStackTrace();
    }

    @Override
    protected void handleWebsocketFrame(WebSocketFrame frame) throws IOException {
        super.handleWebsocketFrame(frame);
    }

    @Override
    public synchronized void sendFrame(WebSocketFrame frame) throws IOException {
        super.sendFrame(frame);
    }
}

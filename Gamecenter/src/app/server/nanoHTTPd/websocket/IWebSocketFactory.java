package app.server.nanoHTTPd.websocket;

import app.server.nanoHTTPd.common.NanoHTTPD.IHTTPSession;

public interface IWebSocketFactory {
    WebSocket openWebSocket(IHTTPSession handshake);
}

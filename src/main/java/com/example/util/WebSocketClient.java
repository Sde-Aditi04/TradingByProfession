package com.example.websocketdemo.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Component
public class WebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);

    private final WebSocketClient webSocketClient;

    public WebSocketClient() {
        this.webSocketClient = new ReactorNettyWebSocketClient();
    }

    public Disposable connectToWebSocket(URI uri, WebSocketHandler handler) {
        return webSocketClient.execute(uri, session -> {
            Mono<Void> completion = session.receive()
                    .map(handler::handleMessage)
                    .then();
            return session.send(Mono.empty()).then(completion);
        }).subscribe(
                null,
                throwable -> logger.error("Error during WebSocket communication", throwable),
                () -> logger.info("WebSocket connection closed")
        );
    }
}

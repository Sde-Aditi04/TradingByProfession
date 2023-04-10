package com.example.websocketdemo.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private final WebSocketHandler webSocketHandler;
    private final WebSocketHandlerAdapter webSocketHandlerAdapter;
    private final ReactorNettyRequestUpgradeStrategy upgradeStrategy;

    private HttpServer httpServer;

    @Autowired
    public WebSocketServer(WebSocketHandler webSocketHandler, WebSocketHandlerAdapter webSocketHandlerAdapter,
                           ReactorNettyRequestUpgradeStrategy upgradeStrategy) {
        this.webSocketHandler = webSocketHandler;
        this.webSocketHandlerAdapter = webSocketHandlerAdapter;
        this.upgradeStrategy = upgradeStrategy;
    }

    @PostConstruct
    public void start() {
        WebHandler webHandler = WebHttpHandlerBuilder
                .webHandler(handler -> upgradeStrategy.upgrade(handler, webSocketHandler, null))
                .build();
        httpServer = HttpServer.create()
                .host("localhost")
                .port(8080)
                .route(routes -> routes.get("/ws", (request, response) -> webHandler.handle(request, response)))
                .bindNow();
        logger.info("WebSocket server started on port {}", httpServer.port());
    }

    @PreDestroy
    public void stop() {
        if (httpServer != null) {
            httpServer.disposeNow();
            logger.info("WebSocket server stopped");
        }
    }
}

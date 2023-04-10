import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private WebSocketClient webSocketClient;
    private SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @EventListener
    public void onWebSocketConnected(SessionConnectedEvent event) {
        System.out.println("Connected to WebSocket server.");
    }

    @EventListener
    public void onWebSocketMessage(MessageReceivedEvent event) {
        String message = event.getMessage().toString();
        System.out.println("Received message from WebSocket server: " + message);
        simpMessagingTemplate.convertAndSend("/topic/dashboard", message);
    }

    @PostConstruct
    public void connectToWebSocketServer() {
        webSocketClient = new StandardWebSocketClient();
        WebSocketHandler webSocketHandler = new AbstractWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                System.out.println("Connection established with WebSocket server.");
                session.sendMessage(new TextMessage("{\"action\":\"subscribe\",\"data\":[\"dashboard\"]}"));
            }
        };
        webSocketClient.doHandshake(webSocketHandler, "ws://13.232.18.39/stream");
    }
}

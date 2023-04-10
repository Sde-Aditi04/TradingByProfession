@Service
public class WebSocketService {

    private final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private final WebSocketMessageRepository webSocketMessageRepository;

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(WebSocketMessageRepository webSocketMessageRepository, SimpMessagingTemplate messagingTemplate) {
        this.webSocketMessageRepository = webSocketMessageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        logger.info("Web socket connection disconnected");
    }

    @MessageMapping("/dashboard")
    public void handleWebSocketMessage(@Payload WebSocketMessage message, SimpMessageHeaderAccessor headerAccessor) {
        logger.info("Received message: {}", message);

        // Save the message to the database
        webSocketMessageRepository.save(message);

        // Forward the message to the client
        messagingTemplate.convertAndSend("/topic/dashboard", message);
    }

}

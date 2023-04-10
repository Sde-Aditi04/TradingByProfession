package com.example.repository;

import com.example.model.WebSocketMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSocketMessageRepository extends JpaRepository<WebSocketMessage, Long> {
    // Define custom query methods here if needed
}

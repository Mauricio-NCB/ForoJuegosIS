package com.example.demo.web;

import static java.lang.String.format;

import org.springframework.stereotype.Controller;

import com.example.demo.model.ChatMessage;
import com.example.demo.model.ChatMessage.MessageType;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;


@Controller
public class WebSocketController {
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;	
	
	@MessageMapping("/chat/{roomId}/sendMessage")
	public ChatMessage sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
		messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
		
		return chatMessage;
	}
	
	@MessageMapping("/chat/{roomId}/addUser")
	public ChatMessage addUser(@DestinationVariable String roomId, @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccesor) {
		
		String currentRoomId = (String) headerAccesor.getSessionAttributes().put("room_id", roomId);
	    if (currentRoomId != null) {
	      ChatMessage leaveMessage = new ChatMessage();
	      leaveMessage.setType(MessageType.LEAVE);
	      leaveMessage.setSender(chatMessage.getSender());
	      messagingTemplate.convertAndSend(format("/channel/%s", currentRoomId), leaveMessage);
	    }

		headerAccesor.getSessionAttributes().put("username", chatMessage.getSender());
		messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
		
		return chatMessage;
	}
}
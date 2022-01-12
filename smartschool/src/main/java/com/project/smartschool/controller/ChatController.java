package com.project.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartschool.dto.request.ChatMessageRequest;
import com.project.smartschool.services.ChatMessageService;
import com.project.smartschool.services.ChatRoomService;

@CrossOrigin
@RestController
public class ChatController {

//	@Autowired
//	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private ChatMessageService chatMessageService;

	@Autowired
	private ChatRoomService chatRoomService;

	 @MessageMapping("/topic/activity")
	 @SendTo("/topic/blog")
	public String processMessage(@Payload String chatMessage) {
		return "aaaaaaaaaaaaaaaa";
//		messagingTemplate.convertAndSendToUser(
//			chatMessage.getRecipientId(),
//			"/queue/messages",
//			new CommentNotificationResponse();
//		);
	}

}

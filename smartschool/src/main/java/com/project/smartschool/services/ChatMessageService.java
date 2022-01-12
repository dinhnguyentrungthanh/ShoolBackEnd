package com.project.smartschool.services;

import java.util.Map;

import com.project.smartschool.dto.request.ChatMessageRequest;
import com.project.smartschool.dto.response.ChatMessageRespone;
import com.project.smartschool.enums.EMessageStatus;

public interface ChatMessageService extends IDtoService<ChatMessageRespone, String, ChatMessageRequest> {

	Long countNewMessage(String senderId, String recipientId);
	
	Map<String, Object> findChatMessages(String senderId, String recipientId);
	
	void updateStatuses(String senderId, String recipientId, EMessageStatus status);
	
}

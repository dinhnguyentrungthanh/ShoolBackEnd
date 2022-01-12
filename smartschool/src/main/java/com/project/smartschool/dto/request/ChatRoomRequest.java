package com.project.smartschool.dto.request;

import com.project.smartschool.entities.ChatMessageEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChatRoomRequest {

	private String id;
	
	private String chatCode;
	
	private String senderId;
	
	private String recipientId;
	
	public ChatMessageEntity convertToEntity() {
		ChatMessageEntity entity = new ChatMessageEntity();
		
		entity.setId(this.id);
		entity.setChatCode(this.chatCode);
		entity.setSenderId(this.senderId);
		entity.setRecipientId(this.recipientId);
		
		return entity;
	}
	
}

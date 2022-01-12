package com.project.smartschool.dto.response;

import java.io.Serializable;

import com.project.smartschool.entities.ChatMessageEntity;
import com.project.smartschool.enums.EMessageStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessageRespone implements Serializable {

	private static final long serialVersionUID = 6578831086437618732L;

	private String id;
	
	private String chatCode;
	
	private String senderId;
	
	private String recipientId;
	
	private String content;
	
	private EMessageStatus messageStatus;
	
	public ChatMessageRespone convertFromEntity(ChatMessageEntity entity) {
		this.id = entity.getId();
		this.chatCode = entity.getChatCode();
		this.senderId = entity.getSenderId();
		this.recipientId = entity.getRecipientId();
		this.content = entity.getContent();
		this.messageStatus = entity.getStatus();
		return this;
	}

}

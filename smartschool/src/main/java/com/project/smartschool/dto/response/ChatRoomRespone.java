package com.project.smartschool.dto.response;

import java.io.Serializable;

import com.project.smartschool.entities.ChatRoomEntity;

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
public class ChatRoomRespone implements Serializable {

	private static final long serialVersionUID = 6578831086437618732L;

	private String id;
	
	private String chatCode;
	
	private String senderId;
	
	private String recipientId;
	
	public ChatRoomRespone convertFromEntity(ChatRoomEntity entity) {
		this.id = entity.getId();
		this.chatCode = entity.getChatCode();
		this.senderId = entity.getSenderId();
		this.recipientId = entity.getRecipientId();
		return this;
	}

}

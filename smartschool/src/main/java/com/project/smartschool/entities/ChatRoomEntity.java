package com.project.smartschool.entities;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "chat_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5869673744043042557L;

	@Field
	private String chatCode;
	
	@Field
	private String senderId;
	
	@Field
	private String recipientId;

}

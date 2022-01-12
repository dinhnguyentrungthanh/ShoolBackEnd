package com.project.smartschool.entities;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.project.smartschool.enums.EMessageStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "chat_message")
@ToString
@Getter
@Setter
public class ChatMessageEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5861972392405934971L;

	@Field
	private String chatCode;
	
	@Field
	private String senderId;
	
	@Field
	private String recipientId;
	
	@Field
	private String content;
	
	@Field
	private EMessageStatus status;
	
}

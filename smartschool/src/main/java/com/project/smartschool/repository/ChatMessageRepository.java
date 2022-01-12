package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.ChatMessageEntity;
import com.project.smartschool.enums.EMessageStatus;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, String> {

	Long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, EMessageStatus status);
	
	List<ChatMessageEntity> findByChatCode(String chatCode);
	
}

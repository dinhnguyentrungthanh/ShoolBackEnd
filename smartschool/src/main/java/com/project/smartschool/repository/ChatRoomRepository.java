package com.project.smartschool.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.ChatRoomEntity;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoomEntity, String> {

	Optional<ChatRoomEntity> findBySenderIdAndRecipientId(String senderId, String recipientId);
	
}

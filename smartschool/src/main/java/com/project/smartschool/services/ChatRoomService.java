package com.project.smartschool.services;

import java.util.Optional;

public interface ChatRoomService {

	Optional<String> findChatCode(String senderId, String recipientId, boolean createIfNotExist);
	
}

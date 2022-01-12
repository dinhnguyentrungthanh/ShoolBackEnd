package com.project.smartschool.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.smartschool.entities.ChatRoomEntity;
import com.project.smartschool.repository.ChatRoomRepository;
import com.project.smartschool.services.ChatRoomService;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Override
	public Optional<String> findChatCode(String senderId, String recipientId, boolean createIfNotExist) {
		return chatRoomRepository
				.findBySenderIdAndRecipientId(senderId, recipientId)
				.map(ChatRoomEntity :: getChatCode)
				.or(() -> {
					if (!createIfNotExist) {
						return  Optional.empty();
					}
					
					String chatCode = String.format("%s_%s", senderId, recipientId);
					
					ChatRoomEntity senderRecipient = ChatRoomEntity
														.builder()
														.chatCode(chatCode)
														.senderId(senderId)
														.recipientId(recipientId)
														.build();
					
					ChatRoomEntity recipientSender = ChatRoomEntity
														.builder()
														.chatCode(chatCode)
														.senderId(senderId)
														.recipientId(recipientId)
														.build();
					
					chatRoomRepository.save(senderRecipient);
					chatRoomRepository.save(recipientSender);
					
					return Optional.of(chatCode);
				});
	}

}

package com.project.smartschool.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.project.smartschool.dto.request.ChatMessageRequest;
import com.project.smartschool.dto.response.ChatMessageRespone;
import com.project.smartschool.entities.ChatMessageEntity;
import com.project.smartschool.enums.EMessageStatus;
import com.project.smartschool.repository.ChatMessageRepository;
import com.project.smartschool.services.ChatMessageService;
import com.project.smartschool.services.ChatRoomService;
import com.project.smartschool.services.UserService;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

	@Autowired
	private ChatMessageRepository chatMessageRepository;
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private MongoOperations mongoOperations;
	
	@Override
	public List<ChatMessageRespone> fetchAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatMessageRespone fetchById(String id) {
		ChatMessageEntity entity = chatMessageRepository
										.findById(id)
										.map(chatMessage -> {
											chatMessage.setStatus(EMessageStatus.DELIVERED);
											return chatMessageRepository.save(chatMessage);
										}).orElse(null);
		return new ChatMessageRespone().convertFromEntity(entity);
	}

	@Override
	public ChatMessageRespone saveUsingDTO(ChatMessageRequest inputDto) {
		//inputDto.setMessageStatus(EMessageStatus.RECEIVED);
		
		//ChatMessageEntity entity = //chatMessageRepository.save(inputDto.convertToEntity());
		
		return null;
	}

	@Override
	public ChatMessageRespone updateUsingDTO(ChatMessageRequest inputDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countNewMessage(String senderId, String recipientId) {
		return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(senderId, recipientId, EMessageStatus.RECEIVED);
	}
	
	@Override
	public Map<String, Object> findChatMessages(String senderId, String recipientId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Map<String, Object> mapUser = new HashMap<String, Object>();
			
			Optional<String> chatCode = chatRoomService.findChatCode(senderId, recipientId, false);
			
			List<ChatMessageRespone> result = new ArrayList<ChatMessageRespone>();
			
			List<ChatMessageEntity> list = chatCode.map(code -> chatMessageRepository.findByChatCode(code)).orElse(new ArrayList<>());
			
			if (list.size() > 0) {
				this.updateStatuses(senderId, recipientId, EMessageStatus.DELIVERED);
				
				for (ChatMessageEntity chatMessageEntity : list) {
					result.add(new ChatMessageRespone().convertFromEntity(chatMessageEntity));
					mapUser.put(chatMessageEntity.getId(), chatMessageEntity.getId());
				}
				
				for (String userId : mapUser.keySet()) {
					mapUser.put(userId, userService.fetchById(userId));
				}
				
				map.put("list", result);
				map.put("users", mapUser);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return map;
	}
	
	@Override
	public void updateStatuses(String senderId, String recipientId, EMessageStatus status) {
		 Query query = new Query(Criteria.where("senderId").is(senderId).and("recipientId").is(recipientId));
		 Update update = Update.update("status", status);
		 
		 mongoOperations.updateMulti(query, update, ChatMessageEntity.class);
	}
	
}

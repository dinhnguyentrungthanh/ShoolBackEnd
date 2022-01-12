package com.project.smartschool.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.smartschool.dto.request.CommentRequest;
import com.project.smartschool.dto.response.CommentReponse;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.UserResponse;
import com.project.smartschool.entities.CommentEntity;
import com.project.smartschool.repository.CommentRepository;
import com.project.smartschool.services.CommentService;
import com.project.smartschool.services.UserService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<CommentEntity> findAll() {
		return null;
	}

	@Override
	public CommentEntity findById(String id) {
		return null;
	}

	@Override
	public CommentEntity save(CommentEntity entity) {
		return null;
	}

	@Override
	public List<CommentEntity> saveAll(List<CommentEntity> entities) {
		return null;
	}

	@Override
	public CommentEntity update(CommentEntity entity) {
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Boolean> deleteByIds(List<String> ids) {
		return null;
	}

	@Override
	public List<CommentReponse> fetchAll() {
		return null;
	}

	@Override
	public CommentReponse fetchById(String id) {
		return null;
	}

	@Override
	public CommentReponse saveUsingDTO(CommentRequest inputDto) {
		try {
			CommentReponse result = new CommentReponse();
			UserResponse user = new UserResponse();
			
			CommentEntity entity = commentRepository.save(inputDto.convertToEntity());
			
			if (entity.getUsername() != null && entity.getUsername().trim().length() > 0) {
				user = userService.findByUsername(entity.getUsername());
			}
			
			return result.convertFromEntity(entity, user, null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public CommentReponse updateUsingDTO(CommentRequest inputDto) {
		try {
			CommentReponse result = new CommentReponse();
			UserResponse user = new UserResponse();
			
			CommentEntity entity = commentRepository.save(inputDto.convertToEntity(commentRepository.findById(inputDto.getId()).orElse(null)));
			
			if (StringUtils.isBlank(user.getUsername())) {
				user = userService.fetchById(entity.getUsername());
			}			
			
			return result.convertFromEntity(entity, user, null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ObjectResponsePaging<CommentReponse> findAll(int page, int size) {
		return null;
	}

	@Override
	public ObjectResponsePaging<CommentReponse> findAll(String testId, String parrentId, Integer page, Integer size) throws Exception {		

		List<CommentReponse> list = new ArrayList<CommentReponse>();
		List<CommentReponse> tempList = new ArrayList<CommentReponse>();		
		Page<CommentEntity> pageEntity;
		
		try {			
			UserResponse user = new UserResponse();
			CommentReponse commentReponse = new CommentReponse();
			
			if (testId != null && testId.trim().length() > 0) {
				pageEntity = commentRepository.findAllByTestIdAndParentId(testId, parrentId, PageRequest.of(page, size));
			} else {
				pageEntity = commentRepository.findAllByParentId(parrentId, PageRequest.of(page, size));
			}
			
			if (pageEntity.getContent() != null && pageEntity.getContent().size() > 0) {
				for (CommentEntity commentEntity : pageEntity.getContent()) {
					if (commentEntity.getUsername() != null && commentEntity.getUsername().trim().length() > 0) {
						user = userService.findByUsername(commentEntity.getUsername());
					}
					
					if (commentEntity.getId() != null && commentEntity.getId().trim().length() > 0) {
						ObjectResponsePaging<CommentReponse> tempMap = this.findAll(testId, commentEntity.getId(), page, size);
						
						if (tempMap != null && tempMap.getTotalElements() > 0) {
							tempList = tempMap.getElements();
							
							if (tempList != null && tempList.size() > 0) {
								list.add(commentReponse.convertFromEntity(commentEntity, user, new HashSet<CommentReponse>(tempList)));
							} else {
								list.add(commentReponse.convertFromEntity(commentEntity, user, null));
							}
						} else {
							list.add(commentReponse.convertFromEntity(commentEntity, user, null));
						}
						
						user = new UserResponse();
						commentReponse = new CommentReponse();
					} else {
						list.add(commentReponse.convertFromEntity(commentEntity, user, null));
					}
				}				
			}
		} catch (Exception e) {
			throw new Exception("Something wrong!");
		}

		return ObjectResponsePaging.<CommentReponse>builder()
				.totalElements(pageEntity.getTotalElements())
				.totalPages(pageEntity.getTotalPages())
				.size(pageEntity.getSize())
				.currentPage(page)
				.elements(list)
				.build();
	}

	@Override
	public boolean isExistedById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CommentEntity> findAllByIds(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

}

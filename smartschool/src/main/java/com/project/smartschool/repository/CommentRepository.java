package com.project.smartschool.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.CommentEntity;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String> {
	
	Page<CommentEntity> findAllByTestIdAndParentId(String testId, String parentId, Pageable pageable);
	
	Page<CommentEntity> findAllByParentId(String parentId, Pageable pageable);
	
}

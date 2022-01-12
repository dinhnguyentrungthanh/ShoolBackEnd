package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.ReviewQuestionEntity;

@Repository
public interface ReviewQuestionRepository extends MongoRepository<ReviewQuestionEntity, String> {
	
	List<ReviewQuestionEntity> findAllByEnableTrueAndKnowledge(String knowledge);
	
	Page<ReviewQuestionEntity> findAllByEnableTrue(Pageable pageable);
	
	Page<ReviewQuestionEntity> findAllByEnableTrueAndKnowledge(Pageable pageable, String knowledge);
}

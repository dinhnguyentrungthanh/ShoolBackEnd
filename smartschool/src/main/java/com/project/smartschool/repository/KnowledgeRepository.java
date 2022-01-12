package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.KnowledgeEntity;

@Repository
public interface KnowledgeRepository extends MongoRepository<KnowledgeEntity, String> {
	
	List<KnowledgeEntity> findAllByEnableTrue();
	
	Page<KnowledgeEntity> findAllByEnableTrue(Pageable pageable);
	
	@Query("{ 'knowledgeName': { $regex: ?0, $options: 'i'} }")
	List<KnowledgeEntity> searchingAll(String keyworks);
	
	boolean existsByUrlAndId(String url, String id);
	
	KnowledgeEntity findByUrl(String url);
}

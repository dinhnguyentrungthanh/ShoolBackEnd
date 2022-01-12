package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.ChapterEntity;

@Repository
public interface ChapterRepository extends MongoRepository<ChapterEntity, String>{

	ChapterEntity findByChaptername(String chapterName);
	
	Page<ChapterEntity> findAllByEnableTrue(Pageable pageable);
	
	List<ChapterEntity> findAllByEnableTrue();
	
	List<ChapterEntity> findAllByEnableFalse();
	
	boolean existsByUrl(String url);
	
	ChapterEntity findByUrl(String url);
	
	Iterable<ChapterEntity> findAllByIdAndEnableTrue(Iterable<String> ids);
}

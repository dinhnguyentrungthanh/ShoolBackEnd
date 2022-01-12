package com.project.smartschool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.BlockEntity;


@Repository
public interface BlockRepository extends MongoRepository<BlockEntity, String>{

	BlockEntity findByBlockname(String blockname);
	
	Optional<BlockEntity> findByIdAndEnableTrue(String id);
	
	Page<BlockEntity> findAllByEnableTrue(Pageable pageable);
	
	List<BlockEntity> findAllByEnableTrue();
	
	List<BlockEntity> findAllByEnableFalse();
	
	boolean existsByUrl(String url);
	
	BlockEntity findByUrl(String url);
	
}

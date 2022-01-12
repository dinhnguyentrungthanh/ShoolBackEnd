package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.TestTypeEntity;

@Repository
public interface TestTypeRepository extends MongoRepository<TestTypeEntity, String> {

	List<TestTypeEntity> findAllByEnableTrue();
	
	List<TestTypeEntity> findAllByEnableTrueAndBlock(String blockId);
	
	Page<TestTypeEntity> findAllByEnableTrue(Pageable pageable);
	
	Page<TestTypeEntity> findAllByEnableTrueAndBlock(Pageable pageable, String blockId);
}

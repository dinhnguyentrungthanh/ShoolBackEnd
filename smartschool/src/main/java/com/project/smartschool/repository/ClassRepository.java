package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.ClassEntity;

@Repository
public interface ClassRepository extends MongoRepository<ClassEntity, String> {

	ClassEntity  findByClassname(String classname);	
	
	Page<ClassEntity> findAllByEnableTrue(Pageable pageable);
	
	List<ClassEntity> findAllByEnableTrue();
}

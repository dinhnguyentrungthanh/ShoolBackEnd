package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.RoleEntity;

@Repository
public interface RoleRepository extends MongoRepository<RoleEntity, String>{
	
	RoleEntity findByName(String name);
	
	Page<RoleEntity> findAllByEnableTrue(Pageable pageable);
	
	List<RoleEntity> findAllByEnableTrue();
}

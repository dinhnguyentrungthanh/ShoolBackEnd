package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.MathDesignEntity;
import com.project.smartschool.entities.RoleGroupEntity;

@Repository
public interface RoleGroupRepository extends MongoRepository<RoleGroupEntity, String>{

	Page<RoleGroupEntity> findAllByEnableTrue(Pageable pageable);
	
	List<RoleGroupEntity> findAllByEnableTrue();

	RoleGroupEntity findByName(String name);
}

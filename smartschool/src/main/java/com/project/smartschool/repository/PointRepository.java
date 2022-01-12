package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.PointEntity;

@Repository
public interface PointRepository extends MongoRepository<PointEntity, String> {

	Page<PointEntity> findAllByEnableTrue(Pageable pageable);
	
	Page<PointEntity> findAllByEnableTrueAndUser(Pageable pageable, String user);
	
	List<PointEntity> findByEnableTrueAndUser(String user);

	List<PointEntity> findByEnableTrue();
	
	Page<PointEntity> findAllByEnableTrueAndType(Pageable pageable, String type);
	
	List<PointEntity> findByEnableTrueAndType(String type);

}

package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.MajorEntity;

@Repository
public interface MajorRepository extends MongoRepository<MajorEntity, String>{

	MajorEntity findByMajorname(String majorname);

	Page<MajorEntity> findAllByEnableTrue(Pageable pageable);
	
	List<MajorEntity> findAllByEnableTrue();
	
	List<MajorEntity> findAllByEnableFalse();
	
	boolean existsByUrl(String url);
	
	MajorEntity findByUrl(String url);
}

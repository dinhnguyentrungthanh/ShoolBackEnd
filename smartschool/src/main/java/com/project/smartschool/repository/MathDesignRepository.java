package com.project.smartschool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.MathDesignEntity;
@Repository
public interface MathDesignRepository extends MongoRepository<MathDesignEntity, String>{

	MathDesignEntity findByMathDesignName(String mathDesignName);
	
	Page<MathDesignEntity> findAllByEnableTrue(Pageable pageable);
	
	List<MathDesignEntity> findAllByEnableTrue();
	
	List<MathDesignEntity> findAllByEnableFalse();
	
	boolean existsByUrl(String url);
	
	MathDesignEntity findByUrl(String url);
}

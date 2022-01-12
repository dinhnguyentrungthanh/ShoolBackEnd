package com.project.smartschool.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.enums.ELevel;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
	
	UserEntity findByIdAndEnableTrue(String id);
	
	UserEntity findByUsername(String username);
	
    UserEntity findByUsernameAndEnableTrue(String username);
    
    UserEntity findByFullnameAndEnableTrue(String fullname);
    
    Page<UserEntity> findAllByEnableTrue(Pageable pageable);
    
    Page<UserEntity> findAllByEnableTrueAndLevel(Pageable pageable, ELevel level);

    UserEntity findByIdOrUsernameAndEnableTrue(String idOrUsername);
    
    List<UserEntity> findByLevelAndEnableTrue(ELevel level);
    
    Page<UserEntity> findAllByEnableTrueAndLevelAndIdIn(Pageable pageable, ELevel level, Collection<?> ids);
}

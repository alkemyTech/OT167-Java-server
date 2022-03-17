package com.alkemy.ong.repository;

import com.alkemy.ong.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Members, Long>{
    
}

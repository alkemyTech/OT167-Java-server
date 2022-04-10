package com.alkemy.ong.repository;
import com.alkemy.ong.model.Comment;
import com.amazonaws.services.dynamodbv2.document.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}

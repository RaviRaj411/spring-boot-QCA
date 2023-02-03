package com.QCA.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.QCA.API.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllBySolutionId(long solution_id);

}

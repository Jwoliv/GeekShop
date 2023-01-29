package com.example.GeekShop.repository.product;

import com.example.GeekShop.model.product.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByProductId(Long id);
}

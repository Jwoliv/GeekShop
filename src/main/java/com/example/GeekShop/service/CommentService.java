package com.example.GeekShop.service;

import com.example.GeekShop.model.Comment;
import com.example.GeekShop.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    public List<Comment> findByProductId(Long id) {
        return commentRepository.findCommentsByProductId(id);
    }
    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}

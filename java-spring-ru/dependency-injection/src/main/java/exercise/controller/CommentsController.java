package exercise.controller;

import exercise.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping(path = "/comments")
public class CommentsController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment getComment(@PathVariable Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
    }

    @PutMapping(path = "/{id}")
    public Comment editComment(@PathVariable Long id, @RequestBody Comment commentData) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
        comment.setPostId(commentData.getPostId());
        comment.setBody(commentData.getBody());
        commentRepository.save(comment);
        return comment;
    }

    @DeleteMapping(path = "/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
// END

package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping(path = "/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<PostDTO> getPosts() {
        var result = new ArrayList<PostDTO>();
        var postList = postRepository.findAll();
        postList.forEach(p -> result.add(postToPostDto(p, commentRepository.findByPostId(p.getId()))));
        return result;
    }

    @GetMapping(path = "/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        var comments = commentRepository.findByPostId(id);
        return postToPostDto(post, comments);
    }


    private PostDTO postToPostDto(Post post, List<Comment> comments) {
        return PostDTO.builder()
                .id(post.getId())
                .body(post.getBody())
                .title(post.getTitle())
                .comments(commentsConvertToDto(comments))
                .build();
    }

    private List<CommentDTO> commentsConvertToDto(List<Comment> comments) {
        var result = new ArrayList<CommentDTO>();
        comments.forEach(s -> result.add(new CommentDTO(s.getId(), s.getBody())));
        return result;
    }

}
// END

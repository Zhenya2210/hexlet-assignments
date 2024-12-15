package exercise.controller;

import exercise.repository.CommentRepository;
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

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping(path = "/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post getPost(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPost(@RequestBody Post post) {
        postRepository.save(post);
    }

    @PutMapping(path = "/{id}")
    public Post editPost(@PathVariable Long id, @RequestBody Post postData) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        post.setTitle(postData.getTitle());
        post.setBody(postData.getBody());
        postRepository.save(post);
        return post;
    }

    @DeleteMapping(path = "/{id}")
    public void removePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }


}
// END

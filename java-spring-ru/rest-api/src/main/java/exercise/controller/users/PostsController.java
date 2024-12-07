package exercise.controller.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

import static exercise.Data.getPosts;
import static java.lang.Integer.parseInt;

// BEGIN
@RestController
@RequestMapping(path = "/api/users")
public class PostsController {

    public List<Post> posts = getPosts();

    @GetMapping("/{id}/posts")
    public List<Post> getPostsByUser(@PathVariable int id) {
        return posts.stream()
                .filter(post -> post.getUserId() == id)
                .collect(Collectors.toList());
    }


    @PostMapping("/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPostByUser(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return post;
    }

}
// END

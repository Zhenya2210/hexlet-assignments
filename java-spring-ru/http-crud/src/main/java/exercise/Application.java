package exercise;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;

@SpringBootApplication
@RestController
@RequestMapping(path = "/posts")
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping
    public List<Post> getPosts() {
        return posts;
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id) {
        return posts.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Post doesn't exist"));
    }

    @PostMapping
    public Post addPost(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @PutMapping("/{id}")
    public Post editPost(@PathVariable String id, @RequestBody Post newPost) {
        var maybePost = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        Post post;
        if (maybePost.isPresent()) {
            post = maybePost.get();
            post.setTitle(newPost.getTitle());
            post.setBody(newPost.getBody());
            return post;
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void removePost(@PathVariable String id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
    // END
}

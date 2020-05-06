package dev.ericksuarez.esblog.post.service.controller;

import dev.ericksuarez.esblog.post.service.model.Post;
import dev.ericksuarez.esblog.post.service.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.OptionalInt;

@Slf4j
@RestController
public class PostController {

    private PostService postService;

    private final String POSTS = "posts";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/getPostPage")
    public Page<Post> getPostPage(@RequestParam(name = "page") OptionalInt page,
                                  @RequestParam(name = "size") OptionalInt size) {
        log.info("event=getPostPageInvoked page={} size={}", page, size);
        var pageable = PageRequest.of(page.getAsInt(), size.getAsInt());
        return postService.getPostPage(pageable);
    }


    @GetMapping("/getPostByUrl/{url}")
    public Post getPostByUrl(@PathVariable("url") String url) {
        log.info("event=getPostByUrlInvoked url={}", url);
        return postService.getPostByUrl(url);
    }

    @PostMapping(POSTS)
    public Post savePost(@Valid @RequestBody Post post) {
        log.info("event=savePostInvoked post={}", post);
        return postService.savePost(post);
    }

    @PutMapping(POSTS + "/{postId}")
    public Post updatePost(@PathVariable("postId") @Min(1) Long postId,
                           @Valid @RequestBody Post post) {
        log.info("event=updatePostInvoked postId={} post={}", postId, post);
        return postService.updatePost(postId, post);
    }

    @DeleteMapping(POSTS + "/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("id") @Min(1) Long postId) {
        log.info("event=deletePostInvoked postId={}", postId);
        return postService.deletePost(postId);
    }
}

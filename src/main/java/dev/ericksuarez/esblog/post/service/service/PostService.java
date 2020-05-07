package dev.ericksuarez.esblog.post.service.service;

import dev.ericksuarez.esblog.post.service.error.PostNotFoundException;
import dev.ericksuarez.esblog.post.service.model.Post;
import dev.ericksuarez.esblog.post.service.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {

    private PostRepository postRepository;

    private final int MAX_LENGTH = 50;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> getPostPage(Pageable pageable) {
        log.info("event=getPostPageInvoked pageable={}", pageable);
        var postPage = truncateContent(postRepository.findAllByActive(true, pageable));
        log.info("event=postsGot postPage={}", postPage);
        return postPage;
    }

    public Post getPostById(Long postId) {
        log.info("event=getPostByIdInvoked postId={}", postId);
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        log.info("event=getPostByIdInvoked postId={}", post);
        return post;
    }

    public Post getPostByUrl(String url) {
        log.info("event=getPostByUrlInvoked url={}", url);
        var post = postRepository.findByUrlAndActive(url, true)
                .orElseThrow(() -> new PostNotFoundException(url));
        log.info("event=postGotByUrl post={}", post);
        return post;
    }

    public Page<Post> getPostsByUser(String userId, Pageable pageable) {
        log.info("event=getPostsByUserInvoked userId={} pageable={}", userId, pageable);
        var postPage = truncateContent(postRepository.findByUserAndActive(userId, true, pageable));
        log.info("event=postPageGotByUser postPage={}", postPage);
        return postPage;
    }

    public Page<Post> getPostByCategory(Long categoryId, Pageable pageable) {
        log.info("event=getPostByCategoryInvoked categoryId={} pageable={}", categoryId, pageable);
        var postPage = truncateContent(postRepository.findByCategoryIdAndActive(categoryId, true, pageable));
        log.info("event=postPageGotByCategory postPage={}", postPage);
        return postPage;

    }

    public Post savePost(Post post) {
        log.info("event=savePostInvoked post={}", post);
        Post postSaved = postRepository.save(createUrl(post));
        log.info("event=postSaved postSaved={}", postSaved);
        return postSaved;
    }

    public Post updatePost(Long postId, Post post) {
        log.info("event=updatePostInvoked postId={} post={}", postId, post);
        post.setId(postId);
        return savePost(post);
    }

    public ResponseEntity<?> deletePost(Long postId) {
        log.info("event=deletePostInvoked");
        return postRepository.findById(postId)
                .map(post -> {
                    post.setActive(false);
                    log.info("event=postToDisable post={}", post);
                    postRepository.save(post);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    private Post createUrl(Post post) {
        return post.toBuilder()
                .url(post.getTitle().toLowerCase().trim().replaceAll("\\s", "-"))
                .active(true)
                .build();
    }

    private Page<Post> truncateContent(Page<Post> posts) {
        log.info("event=truncateContentInvoked posts={}", posts);
        var postTruncated = posts.stream()
                .map(post -> {
                    if (isBigContent.test(post)){
                        return post.toBuilder()
                                .content(post.getContent().substring(0, MAX_LENGTH))
                                .build();
                    } else {
                        return post;
                    }
                })
                .collect(Collectors.toList());
        return new PageImpl<>(postTruncated, posts.getPageable(), posts.getTotalElements());
    }

    private Predicate<Post> isBigContent = post -> post.getContent().length() > MAX_LENGTH;
}

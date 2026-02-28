package org.example.clubmanager.controller;

import org.example.clubmanager.model.Post;
import org.example.clubmanager.service.CalendarService;
import org.example.clubmanager.service.DiscordService;
import org.example.clubmanager.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private DiscordService discordService;

    @Autowired
    private CalendarService calendarService;

    @PostMapping
    public String createPost(@RequestBody Post post) throws ExecutionException, InterruptedException {
        String postId = postService.createPost(post);
        discordService.sharePostToDiscord(post);
        calendarService.addEvent(post);
        return postId;
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id) throws ExecutionException, InterruptedException {
        return postService.getPost(id);
    }

    @GetMapping("/club/{clubId}")
    public List<Post> getPostsByClubId(@PathVariable String clubId) throws ExecutionException, InterruptedException {
        return postService.getPostsByClubId(clubId);
    }

    @PutMapping
    public String updatePost(@RequestBody Post post) throws ExecutionException, InterruptedException {
        return postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable String id) {
        return postService.deletePost(id);
    }
}

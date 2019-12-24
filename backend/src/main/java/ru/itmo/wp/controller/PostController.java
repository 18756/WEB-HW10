package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.PostCreateForm;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import ru.itmo.wp.exception.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/1/posts")
public class PostController extends ApiController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("createPost")
    public Post createPost(@RequestBody @Valid PostCreateForm postCreateForm, BindingResult bindingResult,
                           HttpServletRequest httpServletRequest) {
        if (getUser(httpServletRequest) == null) {
            bindingResult.reject("You should login");
        }
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        return postService.create(getUser(httpServletRequest), postCreateForm.getTitle(), postCreateForm.getText());
    }
}

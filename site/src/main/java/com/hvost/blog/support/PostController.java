package com.hvost.blog.support;

import com.hvost.blog.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by kseniaselezneva on 07/02/15.
 */
@Controller
@RequestMapping("/blog")
public class PostController {

    @Autowired
    private PostService postService;

    //@RequestMapping(method = {GET, POST})
    @RequestMapping(value = "/all")
    public String listBlogs(Model model, @RequestParam(defaultValue = "1") int page){
        //Pageable pageRequst = PageableFactory
        System.out.println("PostController");
        Page<Post> result = postService.getAllPost(page);
        model.addAttribute("articles", result);

        for (Post r : result){
            System.out.println("r->" + r.toString());
        }

        return "/blog/blog_small";
    }

    @RequestMapping()
    public String getAll(Model model){
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<Post> result = postService.getAll(pageRequest);

        model.addAttribute("articles", result);
        return "/blog/blog_small";
    }

    @RequestMapping(value = "/{id:\\d}", method = {GET})
    public String showPost(Model model, @PathVariable Long id){
        System.out.println("id_post = " + id);
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "/blog/blog_single_post";
    }
}

package com.azurealstn.webservice.controller;

import com.azurealstn.webservice.config.auth.LoginUser;
import com.azurealstn.webservice.config.auth.dto.SessionUser;
import com.azurealstn.webservice.controller.dto.PostsResponseDto;
import com.azurealstn.webservice.domain.posts.Posts;
import com.azurealstn.webservice.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("posts", postsService.findAllDesc());
        //SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("posts", postsService.getPostList(pageable));
            model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
            model.addAttribute("next", pageable.next().getPageNumber());
            model.addAttribute("check", postsService.getListCheck(pageable));

        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @GetMapping("/posts/search")
    public String search(String keyword, Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Posts> searchList = postsService.search(keyword);
        model.addAttribute("searchList", searchList);
        return "posts-search";
    }
}

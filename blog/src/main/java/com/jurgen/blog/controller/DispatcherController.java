package com.jurgen.blog.controller;

import com.jurgen.blog.domain.Post;
import com.jurgen.blog.domain.User;
import com.jurgen.blog.formbeans.SignUpFormBean;
import com.jurgen.blog.sevice.PostService;
import com.jurgen.blog.sevice.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DispatcherController {

    private final static int RECENT_POST_COUNT = 10;

    private final Logger logger = LoggerFactory.getLogger(DispatcherController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String toHomePage(Model model) {
        logger.info("request for getting home page");
        List<Post> posts = postService.getRecentPosts(RECENT_POST_COUNT);
        model.addAttribute("posts", posts);
        return "home";
    }

    @RequestMapping(value = {"/signUp", "/signup"}, method = RequestMethod.GET)
    public String toSignUpPage(Model model) {
        model.addAttribute("signUpForm", new SignUpFormBean());
        logger.info("request for getting sign-up page");
        return "signUp";
    }

    @RequestMapping(value = {"/signIn", "/signin"}, method = RequestMethod.GET)
    public String toSignInPage() {
        logger.info("request for getting sign-in page");
        return "signIn";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String toSearchingPage() {
        logger.info("request for getting search page");
        return "search";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView toProfilePage(ModelAndView mav) {
        logger.info("request for getting profile page");
        try {            
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Boolean isCurrentUser = true;
            mav.addObject("user", user);
            mav.addObject("isCurrentUser", isCurrentUser);
        } catch (ClassCastException ex) {
            logger.debug("User isn't authorized: can't cast Principal to User");
        }
        mav.setViewName("profile");
        return mav;
    }

    @RequestMapping(value = "/profile={username}", method = RequestMethod.GET)
    public ModelAndView toProfile(@PathVariable String username, ModelAndView mav) {
        logger.info("request for getting profile with username: " + username);
        User user = (User) userService.getUserWithJoins(username);
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Boolean isCurrentUser = currentUser.equals(user);
            mav.addObject("isCurrentUser", isCurrentUser);
        } catch (ClassCastException ex) {
              logger.debug("User isn't authorized: can't cast Principal to User");
        }
        mav.addObject("user", user);
        mav.setViewName("profile");
        return mav;
    }

    @RequestMapping(value = {"/editPost", "/editpost"}, method = RequestMethod.GET)
    public String toEditPostPage() {
        logger.info("request for getting edit-post page");
        return "editPost";
    }

    @RequestMapping(value = {"/editProfile", "/editprofile"}, method = RequestMethod.GET)
    public String toEditProfilePage() {
        logger.info("request for getting edit-profile page");
        return "editProfile";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String toAdminPage() {
        logger.info("request for getting admin page");
        return "admin";
    }

    public PostService getPostService() {
        return postService;
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}

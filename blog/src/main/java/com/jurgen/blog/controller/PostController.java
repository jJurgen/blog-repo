package com.jurgen.blog.controller;

import com.jurgen.blog.domain.Comment;
import com.jurgen.blog.domain.Post;
import com.jurgen.blog.domain.User;
import com.jurgen.blog.formbeans.AddCommentFormBean;
import com.jurgen.blog.formbeans.WritePostFormBean;
import com.jurgen.blog.service.CommentService;
import com.jurgen.blog.service.PostService;
import com.jurgen.blog.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateSystemException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController {

    private static final int POST_PER_PAGE = 10;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Resource(name = "userService")
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String toPostpage() {
        logger.info("request for getting post page");
        return "post";
    }

    @RequestMapping(value = "/post{postId}", method = RequestMethod.GET)
    public ModelAndView toPostpage(@PathVariable String postId, ModelAndView mav) {
        logger.info("request for getting post page. Post id: " + postId);
        try {
            Long id = Long.parseLong(postId);
            Post post = postService.getPostWithJoins(id);
            if (post != null) {
                mav.addObject("post", post);
            } else {
                logger.debug("Post with id: " + id + " doesn't exist");
                mav.setViewName("redirect:home");
                return mav;
            }
        } catch (NumberFormatException ex) {
            logger.debug("Incorrect postId: " + ex.getMessage());
        }
        mav.setViewName("post");
        return mav;
    }

    @RequestMapping(value = "/page{pageNumber}", method = RequestMethod.GET)
    public ModelAndView getPostPage(@PathVariable String pageNumber, ModelAndView mav) {
        logger.info("request for getting posts page #: " + pageNumber);
        try {
            Integer page = Integer.parseInt(pageNumber);
            if (page <= 0 || page > getLastPageNumber()) {
                page = 1;
            }
            List<Post> posts = postService.getPostPage(page, POST_PER_PAGE);

            mav.addObject("posts", posts);
            mav.addObject("currentPageNumber", page);
            mav.addObject("lastPageNumber", getLastPageNumber());

            mav.setViewName("home");
        } catch (NumberFormatException ex) {
            logger.debug("Incorrect pageNumber: " + ex.getMessage());
            mav.setViewName("redirect:page1");
        }
        return mav;
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ModelAndView addComment(@Valid @ModelAttribute("addCommentFormBean") AddCommentFormBean addCommentFormBean,
            BindingResult result, ModelAndView mav) {
        logger.info("request for adding comment to post with id: " + addCommentFormBean.getPostId());
        Post post = postService.getPostWithJoins(addCommentFormBean.getPostId());
        if (post != null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!result.hasErrors()) {
                commentService.addComment(addCommentFormBean.getComment(), user, post);
                mav.setViewName("redirect:post" + post.getId());
                return mav;
            }
            logger.info("Comment wasn't added. There are " + result.getErrorCount() + " error(s) in data");
            mav.addObject("post", post);
            mav.addObject("addCommentFormBean", addCommentFormBean);
            mav.setViewName("post");
            return mav;
        }
        logger.info("Post with id: " + addCommentFormBean.getPostId() + " doesn't exist");
        mav.setViewName("redirect:home");
        return mav;
    }

    @RequestMapping(value = {"/writePost", "/writepost"}, method = RequestMethod.GET)
    public String toWritePostPage(Model model) {
        logger.info("request for getting write-post page");
        model.addAttribute("writePostFormBean", new WritePostFormBean());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            model.addAttribute("posts", user.getPosts());
        }
        return "writePost";
    }

    @RequestMapping(value = "/writePost", method = RequestMethod.POST)
    public String writePost(@Valid @ModelAttribute("writePostFormBean") WritePostFormBean writePostFormBean,
            BindingResult result, Model model) {
        logger.info("request for writing new post");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!result.hasErrors()) {
            postService.addPost(writePostFormBean.getTitle(), writePostFormBean.getContent(), user);
            List<Post> posts = postService.getUsersPosts(user);
            user.setPosts(posts);
            model.addAttribute("posts", posts);
        } else {
            logger.info("New post wasn't created. There are " + result.getErrorCount() + " error(s) in data");
            model.addAttribute("posts", user.getPosts());
        }
        return "writePost";
    }

    @RequestMapping(value = "/editPost", method = RequestMethod.POST)
    public ModelAndView editPost(@RequestParam(value = "postId") String postId, ModelAndView mav) {
        logger.info("request for editing post with id: " + postId);
        Post currentPost = postService.getPostWithJoins(new Long(postId));
        if (currentPost != null) {
            mav.addObject("post", currentPost);
            mav.setViewName("editPost");
        } else {
            logger.info("Post with id: " + postId + " doesn't exist");
            mav.setViewName("redirect:home");
        }
        return mav;
    }

    @RequestMapping(value = "/removePost", method = RequestMethod.POST)
    public String removePost(@RequestParam(value = "postId") Long postId, Model model) {
        logger.info("request for removing post with id: " + postId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Post post = postService.getPostWithJoins(postId);
            if (user.equals(post.getAuthor()) || userService.hasUserRole(user, "ROLE_MODER")) {
                postService.removePost(post);
                List<Post> posts = postService.getUsersPosts(user);
                user.setPosts(posts);
                model.addAttribute("posts", posts);
                return "writePost";
            }
        }
        logger.info("post with id: " + postId + " didn't remove");
        return "redirect:home";
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    public String updatePost(@Valid @ModelAttribute("updatePostFormBean") WritePostFormBean updatePostFormBean,
            BindingResult result, Model model) {
        logger.info("request for updating post with id: " + updatePostFormBean.getPostId());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!result.hasErrors()) {
            try {
                postService.updatePost(updatePostFormBean.getPostId(),
                        updatePostFormBean.getContent(),
                        updatePostFormBean.getTitle());
                logger.info("post with id:" + updatePostFormBean.getPostId() + " updated");
                List<Post> posts = postService.getUsersPosts(user);
                user.setPosts(posts);
            } catch (HibernateSystemException ex) {
                logger.debug("post with id: " + updatePostFormBean.getPostId() + " wasn't updated. Message: " + ex.getMessage());
            }
        } else {
            logger.info("post with id: " + updatePostFormBean.getPostId() + " wasn't updated. There are " + result.getErrorCount() + " error(s)");
            return "editPost";
        }
        return "redirect:post" + updatePostFormBean.getPostId();
    }

    @RequestMapping(value = "/removeComment", method = RequestMethod.POST)
    public String removeComment(@RequestParam("commentId") Long commentId) {
        logger.info("request for removing comment with id:" + commentId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userService.hasUserRole(user, "ROLE_MODER")) {
            Comment comment = commentService.getComment(commentId);
            Long postId = comment.getPost().getId();
            commentService.removeComment(comment);
            return "redirect:post" + postId;
        }
        return "redirect:home";
    }

    @RequestMapping(value = "/doSearch", method = RequestMethod.GET)
    public String search(@RequestParam(value = "byTitle", required = false, defaultValue = "false") Boolean byTitle,
            @RequestParam(value = "byContent", required = false, defaultValue = "false") Boolean byContent,
            @RequestParam(value = "byAuthor", required = false, defaultValue = "false") Boolean byAuthor,
            @RequestParam(value = "byComments", required = false, defaultValue = "false") Boolean byComments,
            @RequestParam("searchReq") String searchReq,
            Model model) {
        logger.info("request for searching.Param: " + searchReq);
        if (searchReq.trim().length() > 0) {
            Set<Post> result = new HashSet<>();
            if (byTitle) {
                result.addAll(postService.searchByTitle(searchReq));
            }
            if (byContent) {
                result.addAll(postService.searchByContent(searchReq));
            }
            if (byAuthor) {
                result.addAll(postService.searchByAuthor(searchReq));
            }
            if (byComments) {
                result.addAll(postService.searchByComments(searchReq));
            }
            model.addAttribute("result", result);
        }
        return "search";
    }

    private long getLastPageNumber() {
        long lastPage = 0;

        long postCount = postService.countOfPosts();
        if (postCount % POST_PER_PAGE == 0) {
            lastPage = postCount / POST_PER_PAGE;
        } else {
            lastPage = postCount / POST_PER_PAGE + 1;
        }

        return lastPage;
    }

}

package com.jurgen.blog.service;

import com.jurgen.blog.dao.PostDao;
import com.jurgen.blog.domain.Post;
import com.jurgen.blog.domain.User;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "postService")
@Transactional
public class PostService {

    @Autowired
    private PostDao postDao;

    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    public PostService() {
        logger.debug("postService created");
    }

    public List<Post> getPostPage(int pageNumber, int pageSize) {
//        List<Post> allPosts = postDao.getAllPostsSortedByDate();
//        int checkingLimit = pageSize * (pageNumber - 1);
//
//        if (allPosts.size() <= checkingLimit || ((pageNumber <= 0) || (pageSize <= 0))) {
//            throw new IllegalArgumentException("Incorrect input data");
//        }
//
//        if ((allPosts.size() - checkingLimit) < pageSize) {
//            return allPosts.subList(checkingLimit, allPosts.size());
//        }
//
//        return allPosts.subList(checkingLimit, checkingLimit + pageSize);
        System.out.println("Posts: " + postDao.countOfPosts());
        return postDao.getPostsSortedByDate((pageNumber - 1) * pageSize, pageSize);
    }

    public long countOfPosts() {
        return postDao.countOfPosts();
    }

    public List<Post> getRecentPosts(int amount) {
        return postDao.getRecentPosts(amount);
    }

    public List<Post> searchByTitle(String req) {
        return postDao.searchByTitle(req);
    }

    public List<Post> searchByContent(String req) {
        return postDao.searchByContent(req);
    }

    public List<Post> searchByAuthor(String req) {
        return postDao.searchByAuthor(req);
    }

    public List<Post> searchByComments(String req) {
        return postDao.searchByComments(req);
    }

    public void updatePost(Long postId, String content, String title) {
        Post post = postDao.get(postId);
        post.setTitle(title);
        post.setContent(content);
        logger.debug("updating post with title " + post.getTitle().substring(0, Math.min(10, post.getTitle().length() - 1)));
        postDao.update(post);
    }

    public void removePost(Post post) {
        postDao.delete(post);
        logger.debug("post with title " + post.getTitle().substring(0, Math.min(10, post.getTitle().length() - 1)) + " removed");
    }

    public void addPost(String title, String content, User user) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setPostDate(new Date(System.currentTimeMillis()));
        post.setAuthor(user);
        logger.debug("creating of post: " + post.getTitle().substring(0, Math.min(10, post.getTitle().length() - 1)));
        postDao.create(post);

    }

    public List<Post> getUsersPosts(User user) {
        return postDao.getUsersPosts(user);
    }

    public Post getPost(Long id) {
        return postDao.get(id);
    }

    public Post getPostWithJoins(Long id) {
        Post post = postDao.getPostWithJoins(id);
        if (post != null) {
            Collections.sort(post.getComments());
        }
        return post;
    }

    public PostDao getPostDao() {
        return postDao;
    }

    public void setPostDao(PostDao postDao) {
        this.postDao = postDao;
    }

}

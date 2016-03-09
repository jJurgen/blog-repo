package com.jurgen.blog.dao;

import com.jurgen.blog.domain.Post;
import com.jurgen.blog.domain.User;
import java.util.List;

public interface PostDao extends GenericDao<Post, Integer> {

    public List<Post> getUsersPosts(User user);

    public List<Post> getRecentPosts(int amount);

    public List<Post> searchByTitle(String req);

    public List<Post> searchByContent(String req);

    public List<Post> searchByAuthor(String req);

    public List<Post> searchByComments(String req);

    public Post getPostWithJoins(Integer id);
}

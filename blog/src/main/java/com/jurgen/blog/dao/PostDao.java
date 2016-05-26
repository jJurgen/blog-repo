package com.jurgen.blog.dao;

import com.jurgen.blog.domain.Post;
import com.jurgen.blog.domain.User;
import java.util.List;

public interface PostDao extends GenericDao<Post, Long> {

    public List<Post> getUsersPosts(User user);

    public List<Post> getRecentPosts(int amount);
    
    public List<Post> getAllPostsSortedByDate();
    
    public List<Post> getPostsSortedByDate(int start, int count);

    public List<Post> searchByTitle(String req);

    public List<Post> searchByContent(String req);

    public List<Post> searchByAuthor(String req);

    public List<Post> searchByComments(String req);

    public Post getPostWithJoins(Long id);
    
    public long countOfPosts();
}

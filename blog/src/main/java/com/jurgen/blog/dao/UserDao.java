package com.jurgen.blog.dao;

import com.jurgen.blog.domain.User;

public interface UserDao extends GenericDao<User, Long> {

    public boolean isUsernameExists(String username);

    public boolean isEmailExists(String email);

    public User getUserByUsername(String username);

    public User getUserWithJoins(Long id);

    public User getUserWithJoins(String username);
}

package com.jurgen.blog.dao.impl;

import com.jurgen.blog.dao.UserDao;
import com.jurgen.blog.domain.User;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao {

    private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public boolean isUsernameExists(String username) {
        User user = (User) getCurrentSession().createCriteria(type).add(Restrictions.eq("username", username)).uniqueResult();
        logger.debug("Does user with username " + username + " exist? : " + (user != null));
        return user != null;
    }

    @Override
    public boolean isEmailExists(String email) {
        User user = (User) getCurrentSession().createCriteria(type).add(Restrictions.eq("email", email)).uniqueResult();
        logger.debug("Does user with email " + email + " exist? : " + (user != null));
        return user != null;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = (User) getCurrentSession().createCriteria(type).add(Restrictions.eq("username", username)).uniqueResult();
        if (user != null) {
            logger.debug("User with username: " + username + " obtained");
        } else {
            logger.debug("User with username: " + username + " doesn't exist");
        }
        return user;
    }

    @Override
    public User getUserWithJoins(Integer id) {
        User user = get(id);
        if (user != null) {
            Hibernate.initialize(user.getPosts());
            Hibernate.initialize(user.getComments());
            logger.debug("User with id: " + id + " obtained and initialized with " + user.getPosts().size() + " posts and " + user.getComments().size() + " comments");
        } else {
            logger.debug("User with id: " + id + " doesn't exist");
        }
        return user;
    }

    @Override
    public User getUserWithJoins(String username) {
        Session session = getCurrentSession();
        Query query = session.createQuery("from User where username = :username");
        query.setParameter("username", username);
        User user = (User) query.uniqueResult();
        if (user != null) {
            Hibernate.initialize(user.getPosts());
            Hibernate.initialize(user.getComments());
            logger.debug("User with username: " + username + " obtained and initialized with " + user.getPosts().size() + " posts and " + user.getComments().size() + " comments");
        } else {
            logger.debug("User with username: " + username + " doesn't exist");
        }
        return user;
    }

}

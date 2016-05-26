package com.jurgen.blog.service;

import com.jurgen.blog.dao.RoleDao;
import com.jurgen.blog.dao.UserDao;
import com.jurgen.blog.domain.User;
import com.jurgen.blog.domain.UserRole;
import com.jurgen.blog.formbeans.SignUpFormBean;
import java.sql.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "userService")
@Transactional
public class UserService implements UserDetailsService {
    
    private static final String USER_ROLE = "ROLE_USER";
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RoleDao roleDao;
    
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    public UserService() {
        logger.debug("userService created");
    }
    
    public void signUp(SignUpFormBean userInfo) {
        User user = new User();
        UserRole role = roleDao.getRoleByName(USER_ROLE);
        user.setEmail(userInfo.getEmail());
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        user.setRegDate(new Date(System.currentTimeMillis()));
        if (role != null) {
            user.getRoles().add(role);
            logger.debug("creating of user with username: " + user.getUsername());
            userDao.create(user);            
        } else {
            logger.debug("User didn't create, because role " + USER_ROLE + " didn't find");
        }
    }
    
    public boolean hasUserRole(User user, String role) {
        logger.debug("checking user with username: " + user.getUsername() + " for having role: " + role);
        UserRole userRole = roleDao.getRoleByName(role);
        if ((user != null) && (userRole != null)) {
            return user.getRoles().contains(userRole);
        }
        return false;
    }
    
    public void removeUser(Long userId) {
        User user = userDao.get(userId);
        if (user != null) {
            userDao.delete(user);
            logger.debug("User with username: " + user.getUsername() + " removed");
        }
    }
    
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
    
    public User getUserWithJoins(String username) {
        return userDao.getUserWithJoins(username);
    }
    
    public void updateUser(User user) {
        userDao.update(user);
        logger.debug("User with username: " + user.getUsername() + " updated");
    }
    
    public boolean isUsernameExists(String username) {
        return userDao.isUsernameExists(username);
    }
    
    public boolean isEmailExists(String email) {
        return userDao.isEmailExists(email);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userDao.getUserWithJoins(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }
        logger.debug("user loaded by username");
        return user;
    }
    
    public UserDao getUserDao() {
        return userDao;
    }
    
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public RoleDao getRoleDao() {
        return roleDao;
    }
    
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    
}

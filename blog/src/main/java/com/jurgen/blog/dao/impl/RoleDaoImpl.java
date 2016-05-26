package com.jurgen.blog.dao.impl;

import com.jurgen.blog.dao.RoleDao;
import com.jurgen.blog.domain.UserRole;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("roleDao")
public class RoleDaoImpl extends GenericDaoImpl<UserRole, Long> implements RoleDao {

    private static Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

    @Override
    public UserRole getRoleByName(String roleName) {
        Query query = getCurrentSession().createQuery("from UserRole where role = :roleName");
        query.setParameter("roleName", roleName);
        UserRole role = (UserRole) query.uniqueResult();
        if (role == null) {
            logger.debug("Role with name: " + roleName + " doesn't exist");
        } else {
            logger.debug("Role with name: " + roleName + " obtained");
        }
        return role;
    }

}

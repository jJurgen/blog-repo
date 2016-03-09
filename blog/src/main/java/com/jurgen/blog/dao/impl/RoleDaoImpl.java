package com.jurgen.blog.dao.impl;

import com.jurgen.blog.dao.RoleDao;
import com.jurgen.blog.domain.UserRole;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("roleDao")
public class RoleDaoImpl extends GenericDaoImpl<UserRole, Integer> implements RoleDao {

    private static Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
    
    @Override
    public UserRole getRoleByName(String roleName) {
        UserRole role = (UserRole) getCurrentSession().createCriteria(type).add(Restrictions.eq("role", roleName)).uniqueResult();
        if(role != null) logger.debug("Role with name: " + roleName + " obtained");
        else logger.debug("Role with name: " + roleName + " doesn't exist");
        return role;
    }

}

package com.jurgen.blog.dao;

import com.jurgen.blog.domain.UserRole;

public interface RoleDao extends GenericDao<UserRole, Long> {

    public UserRole getRoleByName(String roleName);
}

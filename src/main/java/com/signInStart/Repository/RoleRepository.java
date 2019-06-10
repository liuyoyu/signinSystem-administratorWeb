package com.signInStart.Repository;

import com.signInStart.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    @Query("select r from Role r where r.roleName != 'Admin_SYS' and r.roleName!='User_SYS' and r.roleName!='SupperAdmin_SYS' order by r.createDate")
    List<Role> findAllWithoutSYSROLE();

    Role findByRoleId(Long roleId);
    @Query("select r from Role r where r.roleName in (?1)")
    List<Role> findByRoleNames(String[] roleName);

    @Query("from Role where userType = ?1 order by id asc")
    List<Role> findByUserType(String userType);

}
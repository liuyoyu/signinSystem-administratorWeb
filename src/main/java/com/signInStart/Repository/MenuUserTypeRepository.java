package com.signInStart.Repository;

import com.signInStart.Entity.MenuUserType;
import com.signInStart.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuUserTypeRepository extends JpaRepository<MenuUserType, Long> {

    @Query("select m.menu.menuName from MenuUserType m where m.userType = ?1 order by m.menu.sequence asc")
    List<String> getMenuByUserType(String userType);

    @Query("select m.userType from MenuUserType m where m.menu.menuValue = ?1 order by m.menu.sequence")
    List<String> getUserTypeByMenuValue(String menuValue);
}
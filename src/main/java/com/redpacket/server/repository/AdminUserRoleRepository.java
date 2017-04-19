package com.redpacket.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.redpacket.server.model.AdminUserRole;


/**
 * AdminUserRoleRepository
 */
@Transactional(readOnly = true)
public interface AdminUserRoleRepository extends JpaRepository<AdminUserRole, Long> {

//	@Modifying
//	@Transactional
//	@Query("delete from AdminUserRole role where role.adminUserId = :adminUserId")
//	List<AdminUserRole> deleteByAdminUserId(@Param("adminUserId") Long adminUserId);
}

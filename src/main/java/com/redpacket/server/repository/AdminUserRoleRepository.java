package com.redpacket.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.redpacket.server.model.AdminUserRole;


/**
 * AdminUserRoleRepository
 */
@Transactional(readOnly = true)
public interface AdminUserRoleRepository extends JpaRepository<AdminUserRole, Long>, JpaSpecificationExecutor<AdminUserRole> {

//	@Modifying
//	@Transactional
//	@Query("delete from AdminUserRole role where role.adminUserId = :adminUserId")
//	List<AdminUserRole> deleteByAdminUserId(@Param("adminUserId") Long adminUserId);
}

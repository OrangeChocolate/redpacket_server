package com.redpacket.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.redpacket.server.model.AdminUser;

/**
 * UserRepository
 * 
 * @author vladimir.stankovic
 *
 * Aug 16, 2016
 */
public interface AdminUserRepository extends JpaRepository<AdminUser, Long>, JpaSpecificationExecutor<AdminUser> {
    @Query("select u from AdminUser u left join fetch u.roles r where u.username=:username")
    public Optional<AdminUser> findByUsername(@Param("username") String username);
}

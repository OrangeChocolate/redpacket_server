package com.redpacket.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.AdminUser;
import com.redpacket.server.repository.AdminUserRepository;
import com.redpacket.server.repository.AdminUserRoleRepository;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@Service
public class AdminUserService {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
    private AdminUserRepository userRepository;
	@Autowired
    private AdminUserRoleRepository userRoleRepository;
	
    public Optional<AdminUser> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	public List<AdminUser> findAll() {
		return userRepository.findAll();
	}

	public Page<AdminUser> findAll(Specification<AdminUser> spec, Pageable pageable) {
		return userRepository.findAll(spec, pageable);
	}

	public AdminUser findById(Long id) {
		return userRepository.findOne(id);
	}

	public AdminUser saveOrUpdate(AdminUser adminUser) {
		String rawPassword = adminUser.getPassword();
		String password = encoder.encode(rawPassword);
		adminUser.setPassword(password);
		return userRepository.save(adminUser);
	}

	public void delete(Long id) {
		userRepository.delete(id);
	}

	public void delete(AdminUser adminUser) {
		userRepository.delete(adminUser);
	}
}

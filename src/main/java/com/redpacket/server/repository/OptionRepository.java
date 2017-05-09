package com.redpacket.server.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redpacket.server.model.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

}

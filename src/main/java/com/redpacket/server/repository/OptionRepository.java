package com.redpacket.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.redpacket.server.model.Option;

public interface OptionRepository extends JpaRepository<Option, Long>, JpaSpecificationExecutor<Option> {

}

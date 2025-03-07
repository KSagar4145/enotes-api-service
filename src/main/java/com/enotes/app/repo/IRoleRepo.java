package com.enotes.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.app.entity.Role;

public interface IRoleRepo extends JpaRepository<Role, Integer> {

}

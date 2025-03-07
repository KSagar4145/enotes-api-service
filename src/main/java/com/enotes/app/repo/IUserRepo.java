package com.enotes.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.app.entity.User;

public interface IUserRepo extends JpaRepository<User, Integer> {

}

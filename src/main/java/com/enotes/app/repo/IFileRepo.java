package com.enotes.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.app.entity.FileDetails;

public interface IFileRepo extends JpaRepository<FileDetails, Integer> {

}

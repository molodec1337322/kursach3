package com.example.kursach3.repository;

import com.example.kursach3.models.FileModel;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<FileModel, String> {

}

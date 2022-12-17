package com.example.kursach3.services;

import com.example.kursach3.models.FileModel;
import com.example.kursach3.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service("fileService")
public class FileService {


    private FileRepository fileRepository;

    @Autowired
    public void setFileRepository(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public void save(MultipartFile file) throws IOException {
        FileModel fileEntity = new FileModel();
        fileEntity.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setContentType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setSize(file.getSize());

        fileRepository.save(fileEntity);
    }

    public Optional<FileModel> getFile(String id) {
        return fileRepository.findById(id);
    }

    public List<FileModel> getAllFiles() {
        return fileRepository.findAll();
    }
}

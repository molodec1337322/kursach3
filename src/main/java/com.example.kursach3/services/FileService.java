package com.example.kursach3.services;

import com.example.kursach3.dao.AnswerDAO;
import com.example.kursach3.dao.TicketDAO;
import com.example.kursach3.models.Answer;
import com.example.kursach3.models.FileModel;
import com.example.kursach3.models.Ticket;
import com.example.kursach3.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("fileService")
public class FileService {


    private FileRepository fileRepository;
    private TicketDAO ticketDAO;
    private AnswerDAO answerDAO;

    @Autowired
    public void setFileRepositoryAndDAO(FileRepository fileRepository, TicketDAO ticketDAO, AnswerDAO answerDAO){
        this.fileRepository = fileRepository;
        this.ticketDAO = ticketDAO;
        this.answerDAO = answerDAO;
    }

    public void save(MultipartFile file) throws IOException {
        FileModel fileModel = new FileModel();
        fileModel.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileModel.setContentType(file.getContentType());
        fileModel.setData(file.getBytes());
        fileModel.setSize(file.getSize());

        fileRepository.save(fileModel);
    }

    public void save(MultipartFile file, Ticket ticket) throws IOException {
        FileModel fileModel = new FileModel();
        fileModel.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileModel.setContentType(file.getContentType());
        fileModel.setData(file.getBytes());
        fileModel.setSize(file.getSize());

        fileModel.setTicket(ticket);

        fileRepository.save(fileModel);
    }

    public void save(MultipartFile file, Answer answer) throws IOException {
        FileModel fileModel = new FileModel();
        fileModel.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileModel.setContentType(file.getContentType());
        fileModel.setData(file.getBytes());
        fileModel.setSize(file.getSize());

        fileModel.setAnswer(answer);

        fileRepository.save(fileModel);
    }

    public Optional<FileModel> getFile(String id) {
        return fileRepository.findById(id);
    }

    public List<FileModel> getAllFiles() {
        return fileRepository.findAll();
    }
}

package com.ashar.job.recruitment.management.Service.document;

import com.ashar.job.recruitment.management.Dto.DocumentDto;
import com.ashar.job.recruitment.management.Entity.Document;
import com.ashar.job.recruitment.management.Entity.User;
import com.ashar.job.recruitment.management.Exception.FailedProcessException;
import com.ashar.job.recruitment.management.Exception.NotFoundException;
import com.ashar.job.recruitment.management.Exception.NullException;
import com.ashar.job.recruitment.management.Repository.DocumentRepository;
import com.ashar.job.recruitment.management.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Override
    public DocumentDto upload(MultipartFile file, String email, UUID uuid) throws NullException, NotFoundException, FailedProcessException{
        if ((email==null || email.isEmpty()) && uuid==null ) throw new NullException("Field(s) were missing. Document upload failed.");
        User user;
        if (uuid!=null){
            user = userRepository.findById(uuid).orElseThrow(()->new NotFoundException("User not found."));
        }
        else{
            user = userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("User not found."));
        }

        Document doc = new Document();
        try {
            doc.setData(file.getBytes());
        }catch (Exception e){
            throw new FailedProcessException("Document couldn't be processed.");
        }
        doc.setFileName(file.getOriginalFilename());
        doc.setFileType(file.getContentType());

        Map<String,Object> metadata = new LinkedHashMap<>();
        metadata.put("name",user.getName());
        metadata.put("email",user.getEmail());
        metadata.put("phone",user.getPhone());
        doc.setMetadata(metadata);

        doc.setUser(user);

        return DocumentDto.entityToDto(documentRepository.save(doc));
    }

    @Override
    public DocumentDto get(UUID uuid) throws NullException{
        if (uuid==null) throw new NullException("Please fill the required details.");
        return DocumentDto.entityToDto(documentRepository.findById(uuid).
                orElseThrow(() -> new NotFoundException("Document was not found.")));
    }

    @Override
    public List<DocumentDto> getAll() {
        return documentRepository.findAll().stream()
                .map(doc -> DocumentDto.entityToDto(doc))
                .toList();
    }

    @Override
    @Transactional
    public DocumentDto delete(UUID uuid) {
        Document doc = documentRepository.findById(uuid)
                .orElseThrow(()->new NotFoundException("Document not found."));
        documentRepository.delete(doc);
        return DocumentDto.entityToDto(doc);
    }

    @Override
    public List<DocumentDto> getAllUserDocuments(String email) {
        if (email==null || email.isEmpty()) throw new NullException("Please enter all the required fields.");
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("User not found."));
        System.out.println(user.getDocuments().size());
        return user.getDocuments()
                .stream()
                .map(d -> DocumentDto.entityToDto(d)).collect(Collectors.toList());
    }

}

package ru.kpfu.itis.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.models.Task;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    void uploadFile(MultipartFile multipart, Task task);

    void addFileToResponse(Long fileId, HttpServletResponse response, UserDetails userDetails);
}

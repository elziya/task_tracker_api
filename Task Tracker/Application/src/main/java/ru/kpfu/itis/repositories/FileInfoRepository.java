package ru.kpfu.itis.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.models.FileInfo;

import java.util.Optional;

public interface FileInfoRepository extends CrudRepository<FileInfo, Long> {

    Optional<FileInfo> findByIdAndTask_Project_Account_Id(Long fileId, Long accountId);
}

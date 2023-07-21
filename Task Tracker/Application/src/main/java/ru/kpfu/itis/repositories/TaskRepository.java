package ru.kpfu.itis.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.models.Task;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndProject_Id(Long taskId, Long projectId);

    Page<Task> findAllByProject_Account_IdAndProject_StateAndProject_Id(Long accountId, Project.State state,
                                                                        Long projectId, Pageable pageable);
}

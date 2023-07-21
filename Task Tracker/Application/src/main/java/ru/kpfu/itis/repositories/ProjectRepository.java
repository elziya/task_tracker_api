package ru.kpfu.itis.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.models.Project;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> getProjectByIdAndState(Long id, Project.State state);

    Page<Project> findAllByAccount_IdAndState(Long accountId, Project.State state, Pageable pageable);
}

package ru.kpfu.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "file_info")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long size;

    @Column(nullable = false, length = 200)
    private String contentType;

    @Column(nullable = false, length = 200)
    private String origName;

    @Column(nullable = false, length = 200)
    private String storageName;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}

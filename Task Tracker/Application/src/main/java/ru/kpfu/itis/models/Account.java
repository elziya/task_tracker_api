package ru.kpfu.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    public enum Role {
        USER, ADMIN
    };

    public enum State {
        NOT_CONFIRMED, CONFIRMED, DELETED, BANNED
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    private State state;

    private String firstName;

    private String lastName;

    @Column(nullable = false, length = 150)
    private String email;

    private String password;

    @OneToMany(mappedBy="account")
    private List<Project> projects;

    private UUID confirmCode;
}

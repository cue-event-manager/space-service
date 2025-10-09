package cue.edu.co.jpa.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static cue.edu.co.jpa.constants.TableConstant.SPACE_TYPE_TABLE;

@Data
@Entity
@SoftDelete
@Table(name = SPACE_TYPE_TABLE)
@EntityListeners(AuditingEntityListener.class)
public class SpaceTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
}
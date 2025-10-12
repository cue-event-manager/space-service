package cue.edu.co.jpa.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static cue.edu.co.jpa.constants.TableConstant.SPACE_STATUS_TABLE;

@Entity
@Table(name = SPACE_STATUS_TABLE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
@EntityListeners(AuditingEntityListener.class)
public class SpaceStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    private Boolean canBeReserved;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
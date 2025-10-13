package cue.edu.co.model.spacestatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceStatus {
    private Long id;
    private String name;
    private String description;
    private Boolean canBeReserved;
    private LocalDateTime createdAt;
}

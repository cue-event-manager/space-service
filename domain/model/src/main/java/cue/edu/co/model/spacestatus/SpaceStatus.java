package cue.edu.co.model.spacestatus;
import lombok.Builder;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class SpaceStatus {
    private Long id;
    private String name;
    private String description;
    private Boolean canBeReserved;
    private LocalDateTime createdAt;
}

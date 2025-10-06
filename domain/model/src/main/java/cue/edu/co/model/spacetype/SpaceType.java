package cue.edu.co.model.spacetype;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class SpaceType {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}

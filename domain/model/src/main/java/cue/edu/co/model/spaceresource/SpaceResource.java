package cue.edu.co.model.spaceresource;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class SpaceResource {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}

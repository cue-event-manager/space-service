package cue.edu.co.model.space;
import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacetype.SpaceType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class Space {
    private Long id;
    private String name;
    private Campus campus;
    private SpaceStatus status;
    private SpaceType type;
    private Integer capacity;
    private Set<SpaceResource> resources;
    private LocalDateTime createdAt;
}

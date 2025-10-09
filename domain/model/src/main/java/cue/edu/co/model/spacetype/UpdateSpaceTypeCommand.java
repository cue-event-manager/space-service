package cue.edu.co.model.spacetype;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateSpaceTypeCommand {
    private Long id;
    private String name;
    private String description;
}

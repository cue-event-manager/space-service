package cue.edu.co.model.spacetype;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSpaceTypePageQuery {
    private Integer page;
    private Integer size;
    private String name;
}

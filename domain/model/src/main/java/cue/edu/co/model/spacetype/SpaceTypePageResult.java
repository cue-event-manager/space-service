package cue.edu.co.model.spacetype;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SpaceTypePageResult {
    private List<SpaceType> content;
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
}

package cue.edu.co.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceTypeResponseDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}

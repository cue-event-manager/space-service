package cue.edu.co.model.campus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campus {
    private Long id;
    private String name;
    private String address;
    private LocalDateTime createdAt;
}

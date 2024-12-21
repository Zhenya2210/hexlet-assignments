package exercise.dto;

import java.util.List;

import lombok.*;

// BEGIN
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long id;
    private String title;
    private String body;
    private List<CommentDTO> comments;

}
// END

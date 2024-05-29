package whereQR.project.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import whereQR.project.domain.comment.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CommentInfoDto {
    private final UUID id;
    private final String content;
    private final String author;

    @JsonProperty
    private boolean isAuthor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;
    private Comment.CommentStatus status;
    private List<CommentResponseDto> childComments;



    public CommentInfoDto(UUID id, String content, String author, boolean isAuthor, LocalDateTime createdAt, Comment.CommentStatus status) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.isAuthor = isAuthor;
        this.createdAt = createdAt;
        this.status = status;
    }

    public void setChildComments(List<CommentResponseDto> childComments) {
        this.childComments = childComments;
    }
}
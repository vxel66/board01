package board.domain.Dto;

import board.domain.Entity.BoardEntity;
import lombok.*;

import javax.persistence.Column;

@Getter@Setter@ToString
@AllArgsConstructor@NoArgsConstructor@Builder
public class BoardDto {

    private int b_num;
    private String b_title;
    private String b_contents;
    private String b_createdDate;
    private String b_writer;
    private String b_password;
    private int b_view;

    public BoardEntity toentity(){
        return BoardEntity.builder()
                .b_title(this.b_title)
                .b_contents(this.b_contents)
                .b_writer(this.b_writer)
                .b_password(this.b_password)
                .build();
    }
}

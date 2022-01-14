package board.domain.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
@AllArgsConstructor@NoArgsConstructor
@Builder
public class BoardEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bnum")
    private int bnum;

    @Column(name = "b_title")
    private String b_title;

    @Column(name = "b_contents")
    private String b_contents;

    @Column(name = "b_writer")
    private String b_writer;

    @Column(name = "b_password")
    private String b_password;

    @Column(name = "b_view")
    private int b_view;

    @Column(name = "b_file")
    private  String b_file;
}

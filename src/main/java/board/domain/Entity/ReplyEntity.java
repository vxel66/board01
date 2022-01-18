package board.domain.Entity;

import lombok.*;

import javax.persistence.*;

@Entity // db내 테이블과 매핑 설정
@Table( name = "reply") // 테이블속성 / 테이블이름 설정
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rnum")
    private int rnum;

    @Column(name="rcontents")
    private String rcontents;

    @Column(name = "rpasssword")
    private String rpasssword;

    @ManyToOne
    @JoinColumn(name="bnum")
    private BoardEntity boardEntity;

}

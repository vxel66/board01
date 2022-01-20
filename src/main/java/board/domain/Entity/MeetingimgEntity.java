package board.domain.Entity;


import lombok.*;

import javax.persistence.*;

@Entity // DB내 테이블과 연결
@Table( name = "meetingimg") // 테이블속성 // db에서 사용할 테이블명
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude="meetingEntity")
@Builder
public class MeetingimgEntity {

    //번호
    @Id // 기본키 pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto key
    @Column(name = "mimgnum")
    private int mimgnum;

    @Column(name="mimg")
    private String mimg;

    @ManyToOne
    @JoinColumn(name="meetnum")
    private MeetingEntity meetingEntity;

}

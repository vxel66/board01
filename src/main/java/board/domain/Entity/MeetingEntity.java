package board.domain.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // DB내 테이블과 연결
@Table( name = "meeting") // 테이블속성 // db에서 사용할 테이블명
@AllArgsConstructor //풀생성자 [롬북]
@NoArgsConstructor  //빈생성자 [롬북]
@Getter             //필드 get[룸북]
@Setter             //필드 set[룸북]
@ToString(exclude="meetingimgEntity")           //ToString -> Object [객체의 주소값] : @ToString -> Object [ 객체의 주소값 ]
@Builder            //객체 생성시 안정성 보장 [ new 생성자() <--> Builder : 1.필드 주입순서 X  ]
public class MeetingEntity {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "meetnum")
    private int meetnum;
    //제목
    @Column(name = "meettitle")
    private String meettitle;
    //내용
    @Column(name= "meetcontents")
    private String meetcontents;
    //카테고리
    @Column(name="meetcategorie")
    private String meetcategorie;
    //마감일
    @Column(name= "meetenddate")
    private String meetenddate;
    //
    //모집인원
    @Column(name = "meetmaxnum")
    private int meetmaxnum;
    //등록비번
    @Column(name="meetpassword")
    private String meetpassword;
    //상태
    @Column(name="meetact")
    private String meetact;

    @OneToMany(mappedBy="meetingEntity")
    private List<MeetingimgEntity> meetingimgEntity =  new ArrayList<>();


}

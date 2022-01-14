package board.domain.Entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity,Integer> {

    //JPA 메소드 만들기
    //네이티브쿼리 = 실제 sql
    @Query(nativeQuery=true,value="select * from board where b_title like %:search%")
    Page<BoardEntity> findAlltitle(@Param("search")String search , Pageable pageable);

    @Query(nativeQuery=true,value="select * from board where b_contents like %:search%")
    Page<BoardEntity> findAllcontent(@Param("search")String search , Pageable pageable);

    @Query(nativeQuery=true,value="select * from board where b_writer like %:search%")
    Page<BoardEntity> findAllwriter(@Param("search")String search , Pageable pageable);
}

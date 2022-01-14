package board.service;


import board.domain.Dto.BoardDto;
import board.domain.Entity.BoardEntity;
import board.domain.Entity.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    //글쓰기
    public boolean boardwrite(BoardDto boardDto){

        if(boardDto.getB_title().equals("")||boardDto.getB_contents().equals("")||
                boardDto.getB_writer().equals("")||boardDto.getB_password().equals("")){
            return false;
        }

        boardRepository.save(boardDto.toentity());
        return true;
    }

    //글조회
    public Page<BoardEntity> boardlist(Pageable pageable, String keyword , String search){

        //현재 페이지
        int page = 0;

        if(pageable.getPageNumber()==0) page=0;
        else page = pageable.getPageNumber()-1;
        String b_num="b_num";
        pageable = PageRequest.of(page,5,Sort.by(Sort.Direction.DESC,"bnum"));

        if(keyword!=null&&keyword.equals("b_title")) return boardRepository.findAlltitle(search,pageable);
        if(keyword!=null&&keyword.equals("b_contents")) return boardRepository.findAllcontent(search,pageable);
        if(keyword!=null&&keyword.equals("b_writer")) return boardRepository.findAllwriter(search,pageable);

        return boardRepository.findAll(pageable);

    }
    @Autowired
    HttpServletRequest request ;
    //글상세보기
    @Transactional
    public BoardDto boardview(int b_num){
        Optional<BoardEntity> board = boardRepository.findById(b_num);

        String date = board.get().getCreatedDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));

        HttpSession session = request.getSession();

        if(session.getAttribute(b_num+"")==null){
            board.get().setB_view(board.get().getB_view()+1);

            session.setAttribute(String.valueOf(b_num),1);

            session.setMaxInactiveInterval(60*60*24);
        }

        return BoardDto.builder()
                .b_num(board.get().getBnum())
                .b_title(board.get().getB_title())
                .b_contents(board.get().getB_contents())
                .b_createdDate(date)
                .b_writer(board.get().getB_writer())
                .b_password(board.get().getB_password())
                .b_file(board.get().getB_file())
                .b_view(board.get().getB_view())
                .build();
    }

    //비밀번호 확인
    public boolean pwcheck(int b_num , String b_password ){

        Optional<BoardEntity> board = boardRepository.findById(b_num);
        if(board.get().getB_password().equals(b_password)){
            return true;
        }
        return false;
    }

    //글삭제
    public boolean delete(int b_num){
        Optional<BoardEntity> board = boardRepository.findById(b_num);
        if(board.get().getBnum()==b_num){
            boardRepository.delete(board.get());
            return true;
        }else{
            return false;
        }
    }

    //글수정
    @Transactional // 수정중 오류 발생시 rollback : 취소
    public boolean update(BoardDto boardDto){
        try {
            Optional<BoardEntity> board = boardRepository.findById(boardDto.getB_num());
            board.get().setB_title(boardDto.getB_title());
            board.get().setB_contents(boardDto.getB_contents());
            board.get().setB_file(boardDto.getB_file());
            return true;
        }catch(Exception e){

        }
        return false;

    }
    @Transactional // 수정중 오류 발생시 rollback : 취소
    public boolean deletefile(int b_num){
        Optional<BoardEntity> board = boardRepository.findById(b_num);
        if(board.get().getBnum()==b_num){
            board.get().setB_file(null);
            return true;
        }
        return false;
    }






}

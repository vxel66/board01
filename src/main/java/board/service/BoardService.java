package board.service;


import board.domain.Dto.BoardDto;
import board.domain.Entity.BoardEntity;
import board.domain.Entity.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public ArrayList<BoardDto> boardlist(){

        List<BoardEntity> boardEntityList = boardRepository.findAll();
        ArrayList<BoardDto> boardDtoList = new ArrayList<BoardDto>();
        for(BoardEntity boardEntity : boardEntityList){
            String date = boardEntity.getCreatedDate().format(DateTimeFormatter.ofPattern("yy-mm-dd"));
            String nowdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-mm-dd"));
            if(date.equals(nowdate)){
                date=boardEntity.getCreatedDate().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
            }
            BoardDto boardDto = new BoardDto(
                    boardEntity.getB_num(),
                    boardEntity.getB_title(),
                    boardEntity.getB_contents(),
                    date,
                    boardEntity.getB_writer(),
                    boardEntity.getB_password(),
                    boardEntity.getB_view()
            );
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    //글상세보기
    public BoardDto boardview(int b_num){
        Optional<BoardEntity> board = boardRepository.findById(b_num);

        String date = board.get().getCreatedDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));

        return BoardDto.builder()
                .b_num(board.get().getB_num())
                .b_title(board.get().getB_title())
                .b_contents(board.get().getB_contents())
                .b_createdDate(date)
                .b_writer(board.get().getB_writer())
                .b_password(board.get().getB_password())
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
        if(board.get().getB_num()==b_num){
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
            return true;
        }catch(Exception e){

        }
        return false;

    }




}

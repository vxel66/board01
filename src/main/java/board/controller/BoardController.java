package board.controller;

import board.domain.Dto.BoardDto;
import board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/")
    public String list(Model model) {
        ArrayList<BoardDto> boardDtos = boardService.boardlist();
        model.addAttribute("boardDtos", boardDtos);
        return "boardlist";
    }

    @GetMapping("/board/boardwrite")
    public String boardwrite(){return "boardwrtie";}

    //글쓰기
    @PostMapping("/board/boardwritecontroller")
    public String boardwritecontroller(BoardDto boardDto,Model model){

        boolean result = boardService.boardwrite(boardDto);
        if(result){
            ArrayList<BoardDto> boardDtos = boardService.boardlist();
            model.addAttribute("boardDtos", boardDtos);
            return "boardlist";
        }else{
            return "boardwrtie";
        }
    }

    //글조회
    @GetMapping("/board/boardview/{b_num}")
    public String boardview(@PathVariable("b_num")int b_num, Model model){
        System.out.println("@@@@@@@@@@@@@@@@@  :"+b_num);
        BoardDto boardDto= boardService.boardview(b_num);
        model.addAttribute("boardview",boardDto);
        System.out.println("@@@@@@@@@boardDto@@@  :"+boardDto.getB_contents());
        return "boardview";
    }

    //패스워드 확인
    @PostMapping("/board/pwcheck")
    @ResponseBody
    public String pwcheck(@RequestParam("m_password")String m_password,@RequestParam("b_num")int b_num){

        boolean check = boardService.pwcheck(b_num,m_password);

        if(check){
            return "1";
        }else{
            return "2";
        }

    }

    //글삭제
    @PostMapping("/board/delete")
    @ResponseBody
    public String delete(@RequestParam("b_num")int b_num){
        boolean result = boardService.delete(b_num);
        if(result){
            return "1";
        }else{
            return "2";
        }
    }

    //글수정이동
    @GetMapping("/board/boardupdate/{b_num}")
    public String boardupdate(@PathVariable("b_num")int b_num,Model model){

        BoardDto boardDto = boardService.boardview(b_num);
        model.addAttribute("boardupdate", boardDto);

        return "boardupdate";
    }

    @PostMapping("/board/boardupdatecontroller")
    public String boardupdatecontroller(BoardDto boardDto,Model model){

        boolean result = boardService.update(boardDto);
        BoardDto boardDto1= boardService.boardview(boardDto.getB_num());
        model.addAttribute("boardview",boardDto);

        if(result){
            return "boardview";
        }else{
            model.addAttribute("boardupdate",boardDto);
            return "boardupdate";
        }
    }






}

















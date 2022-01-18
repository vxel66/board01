package board.controller;

import board.domain.Dto.BoardDto;
import board.domain.Entity.BoardEntity;
import board.domain.Entity.ReplyEntity;
import board.service.BoardService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired  //빈 생성 : 자동 메모리 할당
    HttpServletRequest request;

    @GetMapping("/")
    public String list(Model model,@PageableDefault Pageable pageable) {
        //검색 서비스
        String keyword = request.getParameter("keyword");
        String search = request.getParameter("search");

        HttpSession session = request.getSession();

        if(keyword!=null||search!=null){
            session.setAttribute("keyword",keyword);
            session.setAttribute("search",search);
        }else{
            keyword = (String)session.getAttribute("keyword");
            search = (String)session.getAttribute("search");
        }
        Page<BoardEntity> boardDtos = boardService.boardlist(pageable,keyword,search);


        model.addAttribute("boardDtos", boardDtos);

        return "boardlist";
    }

    @GetMapping("/board/boardwrite")
    public String boardwrite(){return "boardwrtie";}

    //글쓰기
    @SneakyThrows
    @PostMapping("/board/boardwritecontroller")
    @ResponseBody
    public String boardwritecontroller(@RequestParam("b_file") MultipartFile file,
                                       @RequestParam("b_title") String b_title,
                                       @RequestParam("b_contents") String b_contents,
                                       @RequestParam("b_writer") String b_writer,
                                       @RequestParam("b_password") String b_password
    ){
        UUID uuid = UUID.randomUUID();// 고유 식별자 객체 난ㄴ수 생성 메소드 호출

        //만약에 파일명에 _ 존재하면 -변경
        String OriginalFilename = file.getOriginalFilename();
        String uuidfile = uuid.toString()+"_"+OriginalFilename.replaceAll("_","-");
        if(!file.getOriginalFilename().equals("")){
            //파일처리
            //String dir = "C:\\Users\\505\\Desktop\\board01\\src\\main\\resources\\static\\upload";
            String dir ="\\home\\ec2-user\\apps\\board01\\src\\main\\resources\\static\\upload";
            String filepath = dir + "\\" + uuidfile;
            file.transferTo(new File(filepath));
        }else{
            uuidfile=null;
        }
        HttpSession session = request.getSession();

        BoardDto boardDto = BoardDto.builder()
                .b_title(b_title)
                .b_contents(b_contents)
                .b_writer(b_writer)
                .b_password(b_password)
                .b_file(uuidfile)
                .build();
        boolean result = boardService.boardwrite(boardDto);
        if(result){
            return "1";
        }
        return "2";
    }

    //글조회
    @GetMapping("/board/boardview/{bnum}")
    public String boardview(@PathVariable("bnum")int b_num, Model model){
        BoardDto boardDto= boardService.boardview(b_num);
        System.out.println("파일명1 :"+boardDto.getB_file());
        if(boardDto.getB_file()!=null&&!boardDto.getB_file().equals("")){
            boardDto.setB_filename(boardDto.getB_file().split("_")[1]);
        }
        System.out.println("파일명2 :"+boardDto.getB_filename());

        List<ReplyEntity> replyEntities = boardService.getreplylist(b_num);

        model.addAttribute("boardview",boardDto);
        model.addAttribute("replyEntities",replyEntities);
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
        if(boardDto.getB_file()!=null&&!boardDto.getB_file().equals("")){
            boardDto.setB_filename(boardDto.getB_file().split("_")[1]);
        }
        model.addAttribute("boardupdate", boardDto);

        return "boardupdate";
    }

    //게시글 수정
    @PostMapping("/board/boardupdatecontroller")
    public String boardupdatecontroller(@RequestParam("b_newfile") MultipartFile b_newfile,
                                        @RequestParam("b_num")int b_num,
                                        @RequestParam("b_title") String b_title,
                                        @RequestParam("b_contents") String b_contents,
                                        @RequestParam("b_file") String b_file
    ){

        System.out.println(b_title);
        if(!b_newfile.getOriginalFilename().equals("")){
            try {
                UUID uuid = UUID.randomUUID(); // 고유 식별자 객체 난수생성 메소드 호출
                String OriginalFilename = b_newfile.getOriginalFilename();
                String uuidfile= uuid.toString()+"_"+OriginalFilename.replaceAll("_","-");
                //String dir = "C:\\Users\\505\\Desktop\\board01\\src\\main\\resources\\static\\upload";
                String dir ="\\home\\ec2-user\\apps\\board01\\src\\main\\resources\\static\\upload";
                String filepath = dir + "\\" + uuidfile;
                b_newfile.transferTo(new File(filepath));
                boardService.update(BoardDto.builder()
                        .b_num(b_num)
                        .b_title(b_title)
                                .b_contents(b_contents)
                                .b_file(uuidfile)
                        .build());
            }catch (Exception e){

            }

        }else{
            boardService.update(BoardDto.builder()
                    .b_num(b_num)
                    .b_title(b_title)
                    .b_contents(b_contents)
                    .b_file( b_file )
                    .build());
        }
        return "redirect:/board/boardview/"+b_num;
    }

    //다운러도
    @GetMapping("/board/filedownload")
    public void filedownload(@RequestParam("b_file")String b_file, HttpServletResponse response) throws IOException {
        System.out.println(b_file);

        //String path = "C:\\Users\\505\\Desktop\\board01\\src\\main\\resources\\static\\upload\\"+b_file;
        String path ="\\home\\ec2-user\\apps\\board01\\src\\main\\resources\\static\\upload\\"+b_file;
        //다운로드
        response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode( b_file.split("_")[1],"utf-8"));

        OutputStream OutputStream = response.getOutputStream();

        FileInputStream inputStream = new FileInputStream(path);

        int read = 0;
        byte[] buffer = new byte[1024*1024]; // 읽어올 바이트 배열을 저장할 배열
        while((read=inputStream.read(buffer))!=-1){
            //파일 쓰기
            OutputStream.write(buffer,0,read);
        }

    }
    @GetMapping("/board/filedeletecontroller")
    @ResponseBody
    public  String filedeletecontroller(@RequestParam("b_num")int b_num
    ,@RequestParam("b_file")String b_file){
        System.out.println(b_num);
        boolean result = boardService.deletefile(b_num);
        if(result){
            //String dir = "C:\\Users\\505\\Desktop\\board01\\src\\main\\resources\\static\\upload";
            String dir ="\\home\\ec2-user\\apps\\board01\\src\\main\\resources\\static\\upload";
            String filepath = dir + "\\" + b_file;
            File deletefile =new File(filepath);
            deletefile.delete();
            return "1";
        }
        return "2";

    }


    @GetMapping("/board/replywrite")
    @ResponseBody
    public String replywrite(@RequestParam("replyinput")String replyinput,
                             @RequestParam("replyPw")String replyPw,
                             @RequestParam("b_num")int b_num){
        boardService.replywrite(b_num,replyinput,replyPw);

        return "1";
    }

    //리플 패스워드 확인
    @GetMapping("/board/replypwconfirm")
    @ResponseBody
    public String replypwconfirm(@RequestParam("replypw")String replypw,
                             @RequestParam("rnum")int rnum){


        boolean reply= boardService.replydelete(rnum,replypw);
        if(reply){
            return "1";
        }else{
            return "2";
        }

    }





}

















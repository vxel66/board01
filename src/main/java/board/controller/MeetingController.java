package board.controller;


import board.domain.Entity.MeetingEntity;
import board.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @GetMapping("/meetinglist")
    public String meetlist(Model model){
        List<MeetingEntity> meetingEntities =  meetingService.getmeetlist();
        model.addAttribute("meetlist" , meetingEntities);
        return "meetinglist";
    }

    @GetMapping("/meetingwrite")
    public String meetingwrite(){
        return "meetingwrite";
    }

    @PostMapping("/meetingwritecontroller")
    public String meetingwritecontroller(MeetingEntity meetingEntity ,
                                         @RequestParam("file")List<MultipartFile> files){
            System.out.println(meetingEntity.getMeetcontents());
            meetingService.meetingwrite(meetingEntity,files);
            return "redirect:/meeting/meetinglist";
    }
    @PostMapping("/meetpassword")
    @ResponseBody
    public String meetpassword(@RequestParam("meetnum")int meetnum,
                               @RequestParam("meetpw")String meetpw){
        System.out.println("con :"+meetnum);
        System.out.println("conpw :"+meetpw);
        boolean result = meetingService.meetdelete(meetnum,meetpw);
        if(result){
            return "1";
        }
        return "2";

    }


}

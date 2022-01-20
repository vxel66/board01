package board.service;

import board.domain.Entity.MeetingEntity;
import board.domain.Entity.MeetingRepository;
import board.domain.Entity.MeetingimgEntity;
import board.domain.Entity.MeetingimgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeetingService {

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    MeetingimgRepository meetingimgRepository;

    public List<MeetingEntity> getmeetlist(){
        List<MeetingEntity> meetingEntities= meetingRepository.findAll();
        return meetingEntities;
    }

    public boolean meetingwrite(MeetingEntity meetEntity , List<MultipartFile> files){

        meetEntity.setMeetact("모집중");

        int meetnum =meetingRepository.save(meetEntity).getMeetnum();


        MeetingEntity savemeet = meetingRepository.findById(meetnum).get();

        //파일처리
        String dir = "C:\\Users\\505\\Desktop\\board01\\src\\main\\resources\\static\\meetimg";
        String uuidfile = null;

        if(files.size() != 0){
            for(MultipartFile file : files){
                UUID uuid = UUID.randomUUID();

                uuidfile = uuid.toString()+"_"+file.getOriginalFilename().replace("_","-");

                String filepath = dir+"\\"+uuidfile;

                try {
                    file.transferTo(new File(filepath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MeetingimgEntity meetingimgEntity = MeetingimgEntity.builder()
                        .mimg(uuidfile)
                        //모임엔티티와 연결
                        .meetingEntity(savemeet)
                        .build();
                //
                int mimgnum = meetingimgRepository.save(meetingimgEntity).getMimgnum();
                savemeet.getMeetingimgEntity().add(meetingimgRepository.findById(mimgnum).get());
            }

        }

        return true;
    }

    public boolean meetdelete(int meetnum , String meetpw ){
        System.out.println("servicen :"+meetnum);
        System.out.println("servicep :"+meetpw);
        Optional<MeetingEntity> meetingEntities = meetingRepository.findById(meetnum);
        List<MeetingimgEntity> meetimgs = meetingEntities.get().getMeetingimgEntity();

        if(meetingEntities.get().getMeetnum()==meetnum&&meetingEntities.get().getMeetpassword().equals(meetpw)){
            for(MeetingimgEntity temp : meetimgs){
                meetingimgRepository.delete(temp);
            }
            System.out.println("delete 0");
            meetingRepository.delete(meetingEntities.get());
            System.out.println("delete 1");
            return true;
        }else{
            System.out.println("delete 2");
            return false;

        }


    }




}

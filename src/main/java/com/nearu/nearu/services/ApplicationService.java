package com.nearu.nearu.services;

import com.nearu.nearu.OriginObject;
import com.nearu.nearu.entity.Application;
import com.nearu.nearu.entity.StudApplication;
import com.nearu.nearu.entity.User;
import com.nearu.nearu.entity.UserInfo;
import com.nearu.nearu.repository.ApplicationRepository;
import com.nearu.nearu.repository.StudApplicationRepository;
import com.nearu.nearu.repository.UserInfoRepository;
import com.nearu.nearu.repository.UserRepository;
import com.nearu.nearu.object.request.ApplicationDto;
import com.nearu.nearu.object.request.ApplicationReadAllResponse;
import com.nearu.nearu.object.request.ApplicationReadResponse;
import com.nearu.nearu.object.request.StudApplicationDto;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApplicationService extends OriginObject {
    private final ApplicationRepository applicationRepository;
    private final StudApplicationRepository studApplicationRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveApplication(ApplicationDto a, User session) throws HttpException {
        Application app = new Application();
        Integer adminNo = session.getUserNo();
        app.setCreatedAt(LocalDateTime.now());
        app.setAdminNo(adminNo);
        app.setConditions(a.getConditions());
        app.setDDay(a.getDDay());
        app.setDurationHours(a.getDurationHours());
        app.setLocation(a.getLocation());
        if (LocalDateTime.now().plusHours(24).isBefore(app.getDDay())) {
            app.setDueDate(a.getDDay().minusHours(24));
        }
        else {
            throw new HttpException("Your appointment date has to be later than 24 hours from now.");
        }
        app.setStatus(false);
        applicationRepository.save(app);
    }

    @Transactional
    public void saveStudApplication(StudApplicationDto stu, User session) {
        Integer userNo = session.getUserNo();
        if(studApplicationRepository.findByApplicationNoAndUserNo(stu.getApplicationNo(), userNo)!=null){
            return;
        }
        Application app = applicationRepository.findByApplicationNo(stu.getApplicationNo());
        if (!app.getStatus()) {
            StudApplication stud = new StudApplication();
            stud.setUserNo(userNo);
            stud.setApplicationNo(stu.getApplicationNo());
            app.setStatus(true);
            studApplicationRepository.save(stud);
            applicationRepository.save(app);
        }
        else {
            // TODO: throw new HttpException("The reservation is already matched with a volunteer.");
        }
    }

    @Transactional
    public void updateApplication(ApplicationDto a) throws HttpException {
        Application app = applicationRepository.findByApplicationNo(a.getApplicationNo());
        if (!app.getStatus()) {
            app.setDDay(a.getDDay());
            app.setConditions(a.getConditions());
            app.setLocation(a.getLocation());
            app.setDurationHours(a.getDurationHours());
            if (LocalDateTime.now().plusHours(24).isBefore(app.getDDay())) {
                app.setDueDate(a.getDDay().minusHours(24));
            }
            else {
                withException("100-001");
               // throw new HttpException("Your appointment date has to be later than 24 hours from now.");
            }
            applicationRepository.save(app);
        }
        else {
            throw new HttpException("The reservation is already matched with a volunteer.");
        }

    }

    @Transactional
    public void updateStudApplication(StudApplicationDto stu){
        StudApplication stud = studApplicationRepository.findByApplicationNoAndUserNo(stu.getApplicationNo(), stu.getUserNo());
        studApplicationRepository.save(stud);
    }

    public ArrayList<ApplicationReadAllResponse> fetchAllApplications(){
        ArrayList<Application> arrList = (ArrayList<Application>) applicationRepository.findAll();
        ArrayList<ApplicationReadAllResponse> list = new ArrayList<>();
        for(int i = 0; i<arrList.size(); i++){
            ApplicationReadAllResponse res = new ApplicationReadAllResponse();
            res.setApp(arrList.get(i));
            if(res.getApp().getStatus()){
                ArrayList<StudApplication> allByApplicationNo = studApplicationRepository.findAllByApplicationNo(res.getApp().getApplicationNo());
                res.setVolunteer(allByApplicationNo.get(i).getUser());
            }
            Integer userNo = arrList.get(i).getAdminNo();
            res.setName(userInfoRepository.findByUserNo(userNo).getName());
            list.add(res);
        }
        return list;
    }

    public ApplicationReadResponse fetchApplicants(Integer applicationNo){
        ApplicationReadResponse res = new ApplicationReadResponse();
        Application app = applicationRepository.findByApplicationNo(applicationNo);
        Integer adminNo = app.getAdminNo();
        res.setName(userInfoRepository.findByUserNo(adminNo).getName());
        res.setApp(app);
        ArrayList<UserInfo> applicants = new ArrayList<>();
        ArrayList<StudApplication> studApplications = studApplicationRepository.findAllByApplicationNo(applicationNo);
        for(int i = 0; i<studApplications.size(); i++){
            Integer userNo = studApplications.get(i).getUserNo();
            UserInfo info = userInfoRepository.findByUserNo(userNo);
            applicants.add(info);
        }
        res.setApplicants(applicants);
        return res;
    }

    @Transactional
    public void deleteApplication(Integer applicationNo){
        studApplicationRepository.deleteAllByApplicationNo(applicationNo);
        applicationRepository.deleteByApplicationNo(applicationNo);
    }

    @Transactional
    public void deleteStudApplication(Integer applicationNo, Integer userNo){
        Application a = applicationRepository.findByApplicationNo(applicationNo);
        if (LocalDateTime.now().isBefore(a.getDDay().minusDays(1L))) {
            studApplicationRepository.deleteByApplicationNoAndUserNo(applicationNo, userNo);
            if (a.getStatus()) {
                a.setStatus(false);
                applicationRepository.save(a);
            }
        }
        else {
            // TODO: Exception needed --> print "You can not cancel your service before shorter than 1 day of reservation."
        }
     }

     public ArrayList<Application> fetchAllByAdmin(User session){
        Integer adminNo = session.getUserNo();
        return applicationRepository.findAllByAdminNo(adminNo);
     }

    public ArrayList<StudApplication> fetchAllByPatient(User session){
        Integer adminNo = session.getUserNo();
        return studApplicationRepository.findAllByUserNo(session.getUserNo());
    }

     public Application fetch(Integer applicationNo){
        return applicationRepository.findByApplicationNo(applicationNo);
     }

     public StudApplication fetchApplicant(Integer applicationNo, Integer userNo){
        return studApplicationRepository.findByApplicationNoAndUserNo(applicationNo, userNo);
     }

     public void cancelApplicant(Integer applicationNo, Integer userNo) {
         Application a = applicationRepository.findByApplicationNo(applicationNo);
         if (LocalDateTime.now().isBefore(a.getDDay().minusDays(1L))) {
             studApplicationRepository.deleteByApplicationNoAndUserNo(applicationNo, userNo);
             a.setStatus(false);
             applicationRepository.save(a);
         }
         else {
            // TODO: Exception needed --> print "The cancellation must happen minimum of 1 day before reservation date"
         }
     }
}

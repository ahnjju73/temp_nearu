package com.nearu.nearu.controller;

import com.nearu.nearu.OriginObject;
import com.nearu.nearu.SessionRequest;
import com.nearu.nearu.config.flows.SessionMapper;
import com.nearu.nearu.entity.*;
import com.nearu.nearu.object.request.UpdateAdminRequest;
import com.nearu.nearu.object.request.UserDto;
import com.nearu.nearu.object.response.SessionResponse;
import com.nearu.nearu.repository.UserPwRepository;
import com.nearu.nearu.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController extends OriginObject {

    private final UserService userService;
    private final UserPwRepository userPasswordsRepository;


    @SessionMapper
    @Transactional
    @PostMapping("/hello-world")
    public User helloWorld(SessionRequest request) {
        return request.getSession();
    }

    @SessionMapper(checkSession = false)
    @Transactional
    @PostMapping("/check-duplication")
    public Map checkDup(SessionRequest request) {
        String id = (String)request.getParam().get("id");
        Map<String, Boolean> res = new HashMap<>();
        res.put("is_duplicated", userService.isDup(id));
        return res;
    }



    @SessionMapper(checkSession = false)
    @Transactional
    @PostMapping("/sign-up")
    public void signUp(SessionRequest request) {
        UserDto map = map(request.getParam(), UserDto.class);
        userService.saveUser(map);
    }

    @Transactional
    @SessionMapper(checkSession = false)
    @PutMapping("/sign-in")
    public SessionResponse loginWithId(SessionRequest request){
        UserDto loginEmailRequest = map(request.getParam(), UserDto.class);
        UserPw userPasswordByUserEmail = userPasswordsRepository.findByUser_UserId(loginEmailRequest.getUserId());
        if(!bePresent(userPasswordByUserEmail))
            withException(""); //존재하지않는 아이디
        userPasswordByUserEmail.loginWithPassword(loginEmailRequest.getPassword());
        User users = userPasswordByUserEmail.getUser();
//        User users = null;
        UserSession userSession = userService.setSession(users);
        SessionResponse sessionResponse = userService.setResponseData(users, userSession.getSessionKey());
        return sessionResponse;
    }
    @SessionMapper
    @GetMapping("/profile")
    public UserDto viewProfile (SessionRequest request) {
        UserDto map = map(request.getParam(), UserDto.class);
        return userService.fetch(request.getSession());
    }

    @SessionMapper
    @Transactional
    @PutMapping("/profile")
    public void editProfile (SessionRequest request) {
        UserDto map = map(request.getParam(), UserDto.class);
        userService.update(map, request.getSession());
    }

    @SessionMapper
    @Transactional
    @PutMapping("/notifications")
    public void editNotif (SessionRequest request) throws HttpException {
        UpdateAdminRequest map = map(request.getParam(), UpdateAdminRequest.class);
        userService.updateNotif(map, request.getSession());
    }

    @SessionMapper
    @GetMapping("/notifications")
    public Notifications viewNotifications (SessionRequest request) {
        UserDto map = map(request.getParam(), UserDto.class);
        return userService.fetchNotif(request.getSession());
    }

    @SessionMapper
    @Transactional
    @DeleteMapping("/leave")
    public void leave (SessionRequest request) {
        UserDto map = map(request.getParam(), UserDto.class);
        userService.leave(request.getSession());
    }

    @SessionMapper
    @Transactional
    @PutMapping("/update-pw")
    public void editPw(SessionRequest request){
        UserDto map = map(request.getParam(), UserDto.class);
        userService.updatePw(map, request.getSession());
    }

}

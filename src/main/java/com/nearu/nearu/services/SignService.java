package com.nearu.nearu.services;

import com.nearu.nearu.SessionRequest;
import com.nearu.nearu.entity.User;
import com.nearu.nearu.entity.UserSession;
import com.nearu.nearu.object.response.SessionResponse;
import com.nearu.nearu.repository.UserRepository;
import com.nearu.nearu.repository.UserSessionRepository;
import com.nearu.nearu.services.internal.Workspace;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignService extends Workspace {

    private final UserRepository usersRepository;
    private final UserSessionRepository userSessionRepository;

    public User checkIfUserByEmail(String email){
        User byEmail = usersRepository.findByUserInfo_Email(email);
        if(bePresent(byEmail)) withException("100-001");
        return byEmail;
    }

    public void logout(User session){
        userSessionRepository.deleteAllByUserNo(session.getUserNo());
    }

    public SessionRequest checkSession(SessionRequest sessionRequest, Boolean throwError){
        try {
            String sessAuthKey = sessionRequest.getSessionKey();
            Claims body = parseClaimsFromSessionKey(sessAuthKey);
            String clientId = body.get("user_id", String.class);
            UserSession userSession = userSessionRepository.findByUser_UserId(clientId);
            if(bePresent(userSession)){
                User user = userSession.getUser();
                String _sessionKey = userSession.getSessionKey();
                if(!sessAuthKey.equals(_sessionKey) && throwError) throwErrorSession();
                sessionRequest.getParam().put("sess_user_no", user.getUserNo());
                sessionRequest.setSession(user);
            }else if(!bePresent(userSession) && throwError) {
                throwErrorSession();
            }
        }catch (Exception e){
            if(throwError) throwErrorSession();
        }
        return sessionRequest;
    }

    private void throwErrorSession() {
        withException("000-002", HttpStatus.UNAUTHORIZED);
    }


    protected Claims parseClaimsFromSessionKey(String sessAuthKey){
        int i = sessAuthKey.lastIndexOf('.');
        String withoutSignature = sessAuthKey.substring(0, i+1);
        Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);
        return untrusted.getBody();
    }

    public UserSession setSession(User session){
        userSessionRepository.deleteAllByUserNo(session.getUserNo());
        UserSession userSession = new UserSession();
        userSession.setUser(session);
        userSession.makeSessionKey();
        userSessionRepository.save(userSession);
        return userSession;
    }


}

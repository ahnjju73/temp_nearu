package com.nearu.nearu;

import com.nearu.nearu.entity.User;
import com.nearu.nearu.util.SESSION;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class SessionRequest {

    private User session;

//    private static final Logger logger = LoggerFactory.getLogger(SessionProvider.class);
//
//    private UserSessionTypes userSessionTypes = UserSessionTypes.WEB;

    private String sessionKey;

    private LocalDateTime sessNow = LocalDateTime.now();

    private Map param = new HashMap();

    private StopWatch stopWatch = new StopWatch();

    private HttpServletRequest httpRequest;

    private HttpServletResponse httpResponse;

    public static SessionRequest makeSessionRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Map post) {
        Map<String, String> pathVariables = (Map<String, String>) httpRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        post.putAll(pathVariables);
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.getStopWatch().start();
        sessionRequest.setSessionKey((String)post.get(SESSION.SESS_AUTH_KEY));
        sessionRequest.setParam(post);
        sessionRequest.setHttpRequest(httpRequest);
        sessionRequest.setHttpResponse(httpResponse);
//        sessionRequest.setUserSessionTypes(UserSessionTypes.getSession((String)post.get("session_type")));
        return sessionRequest;
    }

    public Boolean hasSession(){
        return session != null;
    }

}

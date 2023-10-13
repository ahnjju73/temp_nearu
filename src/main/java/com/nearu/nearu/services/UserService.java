package com.nearu.nearu.services;

import com.nearu.nearu.OriginObject;
import com.nearu.nearu.entity.*;
import com.nearu.nearu.entity.types.FavoriteTypes;
import com.nearu.nearu.entity.types.UserType;
import com.nearu.nearu.object.response.SessionResponse;
import com.nearu.nearu.repository.*;
import com.nearu.nearu.object.request.FavoritesDto;
import com.nearu.nearu.object.request.UpdateAdminRequest;
import com.nearu.nearu.object.request.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends OriginObject{
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserPwRepository userPwRepository;
    private final UserSessionRepository userSessionRepository;
    private final NotificationsRepository notificationsRepository;
    private final FavoritesRepository favoritesRepository;

    public boolean isDup(String id){
        return userRepository.findByUserId(id) != null;
    }

    @Transactional
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserType(UserType.getType(userDto.getType()));
        UserInfo userInfo = new UserInfo();
        userInfo.setAddress(userDto.getAddress());
        userInfo.setName(userDto.getName());
        userInfo.setGender(userDto.getGender());
        userInfo.setCondition(userDto.getCondition());
        userInfo.setEmail(userDto.getEmail());
        userInfo.setPurpose(userDto.getPurpose());
        userInfo.setEmerPhoneNumber(userDto.getEmergencyNum());
        userInfo.setPhoneNumber(userDto.getPhoneNum());
        userInfo.setPresentation(userDto.getPresentation());
        userInfo.setSimilarExp(userDto.getExperience());
        UserPw userPw = new UserPw();
        userPw.makePassword(userDto.getPassword());
        userRepository.save(user);//db에저장
        userInfo.setUserNo(user.getUserNo());
        userPw.setUserNo(user.getUserNo());
        userPwRepository.save(userPw);
        userInfoRepository.save(userInfo);
        Notifications notif = new Notifications();
        notif.setEmailNotif(false);
        notif.setPhoneNotif(false);
        notif.setMsgNotif(false);
        notif.setUserNo(user.getUserNo());
        notificationsRepository.save(notif);
    }

    @Transactional
    public void update(UserDto u, User session){
        Integer userNo = session.getUserNo();
        UserInfo byUserNo = userInfoRepository.findByUserNo(userNo);
        byUserNo.setName(u.getName());
        byUserNo.setEmail(u.getEmail());
        byUserNo.setPhoneNumber(u.getPhoneNum());
        byUserNo.setEmerPhoneNumber(u.getEmergencyNum());
        byUserNo.setPresentation(u.getPresentation());
        userInfoRepository.save(byUserNo);
    }

    @Transactional
    public void updateNotif (UpdateAdminRequest updateAdminRequest, User session) throws HttpException {
        Integer userNo = session.getUserNo();
        Notifications notif = notificationsRepository.findByUserNo(userNo);
        notif.setEmailNotif(updateAdminRequest.getEmailNotification());
        notif.setMsgNotif(updateAdminRequest.getMsgNotification());
        notif.setPhoneNotif(updateAdminRequest.getPhoneNotification());
        List<FavoritesDto> favorites = updateAdminRequest.getFavorites();
        favoritesRepository.deleteAllByUserNo(userNo);
        List<Favorites> favoritesList = new ArrayList<>();
        for (FavoritesDto fd : favorites) {
            Favorites fav = new Favorites();
            fav.setUserNo(userNo);
            fav.setAddress(fd.getAddress());
            fav.setFavoriteTypes(FavoriteTypes.getType(fd.getFavType()));
            favoritesList.add(fav);
        }
        favoritesRepository.saveAll(favoritesList);
        notificationsRepository.save(notif);
    }



    @Transactional
    public void leave(User session){
        Integer userNo = session.getUserNo();
        userInfoRepository.deleteByUserNo(userNo);
        userPwRepository.deleteByUserNo(userNo);
        favoritesRepository.deleteAllByUserNo(userNo);
        notificationsRepository.deleteByUserNo(userNo);
        userRepository.deleteByUserNo(userNo);
    }

    @Transactional
    public void updatePw(UserDto u, User session){
        Integer userNo = session.getUserNo();
        UserPw password = userPwRepository.findByUserNo(userNo);
        password.setPassword(u.getPassword());
        userPwRepository.save(password);
    }

    public User match(String userId, String pw){
        User user = userRepository.findByUserId(userId);
        if(userPwRepository.findByUserNo(user.getUserNo()).getPassword().equals(pw))
            return user;
        return null;
    }
    public UserDto fetch(User user){
        Integer userNo = user.getUserNo();
        UserDto u = new UserDto();
        u.setUserId(user.getUserId());
        u.setType(user.getUserType().getType());
        UserInfo info = userInfoRepository.findByUserNo(userNo);
        u.setName(info.getName());
        u.setGender(info.getGender());
        u.setEmail(info.getEmail());
        u.setPhoneNum(info.getPhoneNumber());
        u.setEmergencyNum(info.getEmerPhoneNumber());
        u.setPresentation(info.getPresentation());
        u.setCondition(info.getCondition());
        u.setExperience(info.getSimilarExp());
        u.setPurpose(info.getPurpose());
        u.setAddress(info.getAddress());
        if (info.getNumRate() == 0) {
            u.setRating(0.0);
        }
        else {
            u.setRating((info.getTotRate().doubleValue())/(info.getNumRate()));
        }
        Notifications notif = notificationsRepository.findByUserNo(userNo);
        u.setPhoneNotification(notif.getPhoneNotif());
        u.setEmailNotification(notif.getEmailNotif());
        u.setMsgNotification(notif.getMsgNotif());
        return u;
    }

    @Transactional
    public Notifications fetchNotif(User user){
        Integer userNo = user.getUserNo();
        Notifications notif = notificationsRepository.findByUserNo(userNo);

        return notif;
    }
    @Transactional
    public void saveFavorites(FavoritesDto f, User session) {
        Favorites favorites = new Favorites();
        Integer userNo = session.getUserNo();
        favorites.setUserNo(userNo);
        favorites.setAddress(f.getAddress());
        favoritesRepository.save(favorites);
    }

    public ArrayList<Favorites> fetchAllFavorites (User session) {
        Integer userNo = session.getUserNo();
        return favoritesRepository.findAllByUserNo(userNo);
    }

    @Transactional
    public void updateFavorites(FavoritesDto fav){
        Integer favoriteNo = fav.getFavoriteNo();
        Favorites favorites = favoritesRepository.findByFavoriteNo(favoriteNo);
        favorites.setAddress(fav.getAddress());
        favoritesRepository.save(favorites);
    }

    @Transactional
    public void deleteFavorites (Integer favoriteNo){
        favoritesRepository.deleteByFavoriteNo(favoriteNo);
    }





    public UserSession setSession(User session/*, UserSessionTypes userSessionTypes*/){
//        userSessionRepository.deleteAllByUserNo(session.getUserNo()/*, userSessionTypes*/);
        UserSession userSession = new UserSession();
        userSession.setUser(session);
        userSession.makeSessionKey();
        String sessionKey = userSession.getSessionKey();
        System.out.println(sessionKey.length());
//        userSession.setSessionTypes(userSessionTypes);
        userSessionRepository.save(userSession);
        return userSession;
    }



    public SessionResponse setResponseData(User user, String sessionKey){
        SessionResponse sessionResponseDto = new SessionResponse();
        sessionResponseDto.setUserId(user.getUserId());
        UserInfo info = userInfoRepository.findByUserNo(user.getUserNo());
        sessionResponseDto.setUsername(info.getName());
        sessionResponseDto.setEmail(info.getEmail());
        sessionResponseDto.setSessionKey(sessionKey);
        return sessionResponseDto;
    }



}

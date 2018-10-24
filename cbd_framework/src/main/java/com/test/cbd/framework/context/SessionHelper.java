package com.test.cbd.framework.context;

import com.google.gson.Gson;
import com.test.cbd.framework.bean.SessionInfo;
import com.test.cbd.framework.bean.UserInfo;
import com.test.cbd.framework.redis.RedisDao;
import com.test.cbd.framework.response.HttpResponse;
import com.test.cbd.framework.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 功能：Session辅助类，目前使用redis
 *      (sessionId在前端可能叫token，Jsessionid等等，但都是session技术)
 * 作者：chenjiefeng
 * 日期：2018/3/29
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Component
public class SessionHelper {

    private static String SSO_SESSION_PREFIX ="sso:session:";
    private static String SSO_USERINFO_PREFIX ="sso:userInfo:";
    private static String SSO_LOCK_PREFIX ="sso:lock:";

    @Autowired
    private RedisDao redisDao;

    private boolean trySsoLock(String sessionId){
        String lockKey = SSO_LOCK_PREFIX + sessionId;
        boolean lockRes = redisDao.putIfAbsent(lockKey, System.nanoTime()+"");
        if(lockRes==true){
            lockRes = redisDao.expire(lockKey, 5, TimeUnit.SECONDS);//5秒 ,
        }
        return lockRes;
    }

    private void releaseSsoLock(String sessionId){
        redisDao.delete(SSO_LOCK_PREFIX + sessionId);
    }

    public SessionInfo getSession(String sessionId){
        return  (new Gson()).fromJson(redisDao.get(SSO_SESSION_PREFIX + sessionId), SessionInfo.class);
    }

    public void deleteSession(String sessionId){
        redisDao.delete(SSO_SESSION_PREFIX + sessionId);
    }

    public boolean updateSessionExpireTime(String sessionId, long expireTime){
        if(expireTime > 0){
            return redisDao.expire(SSO_SESSION_PREFIX + sessionId, expireTime,  TimeUnit.SECONDS);
        }
        return false;
    }


    public UserInfo getUserInfo(String userId){
        return (new Gson()).fromJson(redisDao.get(SSO_USERINFO_PREFIX + userId), UserInfo.class);
    }

    public void deleteUserInfo(String userId){
        redisDao.delete(SSO_USERINFO_PREFIX + userId);
    }

    public boolean updateUserInfoExpireTime(String userId, long expireTime){
        if(expireTime > 0){
            return redisDao.expire(SSO_USERINFO_PREFIX + userId, expireTime,  TimeUnit.SECONDS);
        }
        return false;
    }

    //超时时间是业务，不在这里设置
    public HttpResponse<String> createSession(SessionInfo sessionInfo, UserInfo userInfo, boolean force){
        HttpResponse<String> response = new HttpResponse();
        if(sessionInfo == null || userInfo == null){
            response.setResult(false);
            response.setCode(ResponseCode.ERROR_CODE);
            response.setMessage("账号登录异常");
            return response;
        }

        if(!force && !userInfo.isUserMultiLogin()){//不强制登录
            UserInfo cacheUser = getUserInfo(userInfo.getUserId());
            if(cacheUser != null){
                String sessionId = cacheUser.getCurSessionId();
                if(sessionId != null){
                    SessionInfo curSessionInfo = getSession(sessionId);
                    if(curSessionInfo != null){
                        response.setResult(false);
                        response.setCode(ResponseCode.USER_USING.getCode());
                        response.setMessage("账号正在其它地方使用");
                        return response;
                    }
                }
            }
        }

        String sessionId = getUuid();
        if(trySsoLock(sessionId)){
            try {
                //缓存session
                sessionInfo.setSessionId(sessionId);
                boolean isNewSession = redisDao.putIfAbsent(SSO_SESSION_PREFIX + sessionId, (new Gson()).toJson(sessionInfo));
                if(isNewSession){
                    long expireTime = sessionInfo.getExpireTime();
                    if (expireTime > 0) {
                        redisDao.expire(SSO_SESSION_PREFIX + sessionId, expireTime, TimeUnit.SECONDS);
                    }
                    //覆盖userInfo
                    userInfo.setCurSessionId(sessionId);
                    redisDao.put(SSO_USERINFO_PREFIX + userInfo.getUserId(), (new Gson()).toJson(userInfo));
                    long userExpireTime = userInfo.getExpireTime();
                    if(userExpireTime>0){
                        redisDao.expire(SSO_USERINFO_PREFIX + userInfo.getUserId(), userExpireTime, TimeUnit.SECONDS);
                    }

                    response.setResult(true);
                    response.setCode(ResponseCode.SUCCESS.getCode());
                    response.setMessage("登录成功");
                    response.setData(sessionId);
                    return response;
                }
            }catch (Exception e){

            }finally {
                releaseSsoLock(sessionId);
            }
        }
        response.setResult(false);
        response.setCode(ResponseCode.FAILURE.getCode());
        response.setMessage("账号登录失败");
        return response;
    }

    public void updateSession(SessionInfo sessionInfo, UserInfo userInfo, boolean userOver){
        if(sessionInfo != null && userInfo != null){
            String sessionId = sessionInfo.getSessionId();
            redisDao.put(SSO_SESSION_PREFIX + sessionInfo.getSessionId(), (new Gson()).toJson(sessionInfo) );
            long expireTime = sessionInfo.getExpireTime();
            if (expireTime > 0) {
                redisDao.expire(SSO_SESSION_PREFIX + sessionId, expireTime, TimeUnit.SECONDS);
            }
            if(userOver){
                redisDao.put(SSO_USERINFO_PREFIX + userInfo.getUserId(), (new Gson()).toJson(userInfo));
            }
            long userExpireTime = userInfo.getExpireTime();
            if(userExpireTime>0){
                redisDao.expire(SSO_USERINFO_PREFIX + userInfo.getUserId(), userExpireTime, TimeUnit.SECONDS);
            }
        }
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}

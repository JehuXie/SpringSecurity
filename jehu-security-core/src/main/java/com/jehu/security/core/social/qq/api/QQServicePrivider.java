package com.jehu.security.core.social.qq.api;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author JehuXie
 * @Title: QQServicePrivider
 * @Package com.jehu.security.core.social.qq.api
 * @Description: ${todo}
 * @date 2018/3/14 0014下午 4:41
 */
public class QQServicePrivider extends AbstractOAuth2ServiceProvider<QQ> {


    private String appId;

    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServicePrivider(String appId,String appSecret) {
        super(new OAuth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}

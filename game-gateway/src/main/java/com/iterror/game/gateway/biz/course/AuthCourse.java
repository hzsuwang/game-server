package com.iterror.game.gateway.biz.course;

import org.springframework.stereotype.Service;

import com.iterror.game.common.tcp.closure.BaseCourse;
import com.iterror.game.common.tcp.closure.MessageCourse;
import com.iterror.game.gateway.tcp.bto.xip.AuthReq;

@Service("authCourse")
public class AuthCourse extends BaseCourse implements MessageCourse<AuthReq> {

    @Override
    public void execute(AuthReq req) {
        logger.info("AuthCourse req=" + req);
    }

}

package com.iterror.game.gateway.biz.course;

import org.springframework.stereotype.Service;

import com.iterror.game.common.tcp.closure.BaseCourse;
import com.iterror.game.common.tcp.closure.MessageCourse;
import com.iterror.game.gateway.tcp.bto.xip.HeartbeatReq;


@Service("heartbeatCourse")
public class HeartbeatCourse extends BaseCourse implements MessageCourse<HeartbeatReq> {

    @Override
    public void execute(HeartbeatReq req) {
    }

}

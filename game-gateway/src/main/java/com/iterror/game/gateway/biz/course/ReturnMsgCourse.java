package com.iterror.game.gateway.biz.course;

import org.springframework.stereotype.Service;

import com.iterror.game.common.tcp.closure.BaseCourse;
import com.iterror.game.common.tcp.closure.MessageCourse;
import com.iterror.game.gateway.tcp.bto.xip.ReturnMsgReq;


@Service("returnMsgCourse")
public class ReturnMsgCourse extends BaseCourse implements MessageCourse<ReturnMsgReq> {


    @Override
    public void execute(ReturnMsgReq req) {

    }

}

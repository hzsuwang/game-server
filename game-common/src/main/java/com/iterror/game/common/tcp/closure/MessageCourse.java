package com.iterror.game.common.tcp.closure;

public interface MessageCourse<Request> {

    /**
     * @param
     */
    public void execute(Request req);
}

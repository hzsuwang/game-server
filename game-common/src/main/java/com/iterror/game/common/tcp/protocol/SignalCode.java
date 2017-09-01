package com.iterror.game.common.tcp.protocol;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TODO
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SignalCode {

    int messageCode();
}

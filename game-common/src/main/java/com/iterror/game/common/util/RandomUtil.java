package com.iterror.game.common.util;

import java.util.Random;

/**
 * Created by tony.yan on 2017/9/4.
 */
public class RandomUtil {

    private static Random random = new Random();

    public static int getRandomNum(int num) {
        return random.nextInt(num);
    }
}

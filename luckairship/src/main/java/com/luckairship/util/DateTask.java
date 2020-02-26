package com.luckairship.util;

import java.util.Date;
import java.util.TimerTask;

public class DateTask extends TimerTask {
    public void run() {
        System.out.println("任務時間：" + new Date());
    }
}
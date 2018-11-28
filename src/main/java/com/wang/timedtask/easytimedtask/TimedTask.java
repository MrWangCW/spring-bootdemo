package com.wang.timedtask.easytimedtask;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务类
 * Configuration          //证明这个类是一个配置文件
 * EnableScheduling       //打开quartz定时器总开关
 * Async                 多线程执行，不互相干扰
 * Created by wangyanwei on 2018/7/6.
 *
 * @author wangyanwei
 * @version 1.0
 */
/*@Configuration
@EnableScheduling
@Async*/
public class TimedTask {
    /**
     * cron只支持六位字符
     * 1.秒（0~59）
     * 2.分钟（0~59）
     * 3.小时（0~23）
     * 4.天（月）（0~31，但是你需要考虑你月的天数）
     * 5.月（0~11）
     * 6.天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
     */
    //@Scheduled(cron = "0/6 * * * * *")
    public void cronTest(){
        try {
            TimeUnit.MINUTES.sleep(1);
            System.out.println("Job执行，当前时间111"+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单位是毫秒
     *
     * @Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
     * @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
     * @Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
     */
    //@Scheduled(fixedRate = 5000)
    public void fixedRateTest(){
        Date date = new Date();
        System.out.println("Job执行，当前时间222"+date);
    }
}

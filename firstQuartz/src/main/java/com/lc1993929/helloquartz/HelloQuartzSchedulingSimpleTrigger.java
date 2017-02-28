package com.lc1993929.helloquartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.spi.MutableTrigger;

import java.util.Date;

/**
 * Created by LiuChang on 2017/2/28.
 */
public class HelloQuartzSchedulingSimpleTrigger {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(HelloQuartzJob.class).withIdentity("helloQuartzJob", Scheduler.DEFAULT_GROUP).build();

        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        simpleScheduleBuilder.withIntervalInMilliseconds(1000).withRepeatCount(1);//每隔一秒触发一次，额外触发1次
        //定时任务中要使用的参数
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name", "liuchang");

        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleTrigger", Scheduler.DEFAULT_GROUP)
                .withSchedule(simpleScheduleBuilder)
                .usingJobData(jobDataMap)
                .startAt(new Date(System.currentTimeMillis() + 5000))//触发后5秒再执行job
                .build();

        //注册并调度
        scheduler.scheduleJob(jobDetail, simpleTrigger);

        scheduler.start();

        Thread.sleep(7 * 1000);//当前线程等待7秒

        scheduler.shutdown(true);

        System.out.println("结束运行");
    }
}

package com.lc1993929.helloquartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import java.util.Date;


/**
 * Created by LiuChang on 2017/2/28.
 */
public class HelloQuartzSchedulingCronTrigger {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(HelloQuartzJob.class).withIdentity("helloQuartzJob", Scheduler.DEFAULT_GROUP).build();
        //排除每天的10：0：0
        HolidayCalendar holidayCalendar = new HolidayCalendar();
        holidayCalendar.addExcludedDate(DateBuilder.dateOf(10, 0, 0));
        scheduler.addCalendar("calendar", holidayCalendar, true, false);

        String cronExpression = "*/3 * * * * ?";//每三秒执行一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleTrigger", Scheduler.DEFAULT_GROUP)
                .withSchedule(cronScheduleBuilder)
                .startNow()//触发后立即执行
                .build();

        //注册并调度
        scheduler.scheduleJob(jobDetail, cronTrigger);

        scheduler.start();

        Thread.sleep(7 * 1000);//当前线程等待7秒

        scheduler.shutdown(true);

        System.out.println("结束运行");
    }
}

package com.lc1993929.helloquartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by LiuChang on 2017/2/28.
 */
public class HelloQuartzJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("hello quartz!- executing its JOB at " +
                new Date() + " by " + context.getTrigger().getCalendarName()
                + context.getMergedJobDataMap().getString("name")
        );
    }
}

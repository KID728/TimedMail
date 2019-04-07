package com.kid.mail;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayVultr {
    public static Logger log = LoggerFactory.getLogger(PayVultr.class);

    public static void main(String[] args) {
        timedDeal();
    }

    private static void timedDeal() {
        try {
            JobDetail job = JobBuilder.newJob(SendMailJob.class).withIdentity("SendMailJob").build();

            // 每月1日14:12分运行
            CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0 12 14 1 * ? 2019-2021");
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("SendMailJobTrigger").startNow()
                    .withSchedule(cronSchedule)
                    .build();

            StdSchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
            log.info("[PayVultr][timedDeal] scheduler start");
        } catch (SchedulerException se) {
            log.error("[PayVultr][timedDeal] Failed to timed deal", se);
        } catch (Exception e) {
            log.error("[PayVultr][timedDeal] Failed to timed deal", e);
        }
    }


}

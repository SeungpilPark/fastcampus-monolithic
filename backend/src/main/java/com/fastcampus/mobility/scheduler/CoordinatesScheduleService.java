package com.fastcampus.mobility.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoordinatesScheduleService {

  private static String GROUP_PREFIX = "vehicle-coordinates";
  private final Scheduler scheduler;

  @Autowired
  public CoordinatesScheduleService(
      final Scheduler scheduler
  ) {
    this.scheduler = scheduler;
  }

  public void addSchedule(final Long drivingId) throws SchedulerException {
    String jobName = String.format("%s", drivingId);

    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.putAsString("drivingId", drivingId);

    JobDetail jobDetail = JobBuilder.newJob(CoordinatesUpdateJob.class)
        .withIdentity(jobName, GROUP_PREFIX)
        .usingJobData(jobDataMap)
        .storeDurably()
        .build();

    CronTrigger cronTrigger = TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity(jobName, GROUP_PREFIX)
        .withSchedule(
            CronScheduleBuilder.cronSchedule(this.generateCronExpression())
        ).build();
    scheduler.scheduleJob(jobDetail, cronTrigger);
  }

  public void removeSchedule(final Long drivingId) throws SchedulerException {
    String jobName = String.format("%s", drivingId);
    JobKey jobKey = new JobKey(jobName, GROUP_PREFIX);
    scheduler.deleteJob(jobKey);
  }

  private String generateCronExpression() {
    return "0/10 * * ? * * *";
  }
}


package com.fastcampus.mobility.scheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DispatchScheduleService {

  private static String GROUP_PREFIX = "dispatch-request";
  private final Scheduler scheduler;

  @Autowired
  public DispatchScheduleService(
      final Scheduler scheduler
  ) {
    this.scheduler = scheduler;
  }

  public void addSchedule(final Long drivingId, final Integer attempts) throws SchedulerException {
    String jobName = String.format("%s-%s", drivingId, attempts);

    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.putAsString("drivingId", drivingId);
    jobDataMap.putAsString("attempts", attempts);

    JobDetail jobDetail = JobBuilder.newJob(DispatchRequestJob.class)
        .withIdentity(jobName, GROUP_PREFIX)
        .usingJobData(jobDataMap)
        .storeDurably()
        .build();

    LocalDateTime startAt;
    if (attempts == 1) {
      startAt = LocalDateTime.now();
    } else {
      startAt = LocalDateTime.now().plusMinutes(1L);
    }

    SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity(jobName, GROUP_PREFIX)
        .startAt(Date.from(startAt.atZone(ZoneId.systemDefault()).toInstant()))
        .withSchedule(
            SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
        .build();
    scheduler.scheduleJob(jobDetail, simpleTrigger);
  }

  public void removeSchedule(final Long drivingId, final Integer attempts)
      throws SchedulerException {
    String jobName = String.format("%s-%s", drivingId, attempts);
    JobKey jobKey = new JobKey(jobName, GROUP_PREFIX);
    scheduler.deleteJob(jobKey);
  }
}


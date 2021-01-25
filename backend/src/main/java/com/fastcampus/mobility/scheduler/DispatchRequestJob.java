package com.fastcampus.mobility.scheduler;

import com.fastcampus.mobility.service.spec.DrivingCommandService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DispatchRequestJob extends QuartzJobBean {

  private final DrivingCommandService drivingCommandService;

  @Autowired
  public DispatchRequestJob(
      final DrivingCommandService drivingCommandService
  ) {
    this.drivingCommandService = drivingCommandService;
  }

  @Override
  protected void executeInternal(
      JobExecutionContext jobExecutionContext) {
    JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
    long drivingId = jobDataMap.getLongFromString("drivingId");
    int attempts = jobDataMap.getIntFromString("attempts");
    drivingCommandService.dispatchRequest(drivingId, attempts);
  }
}

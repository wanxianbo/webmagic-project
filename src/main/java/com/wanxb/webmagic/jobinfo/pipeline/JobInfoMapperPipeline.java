package com.wanxb.webmagic.jobinfo.pipeline;

import com.wanxb.webmagic.jobinfo.entity.JobInfo;
import com.wanxb.webmagic.jobinfo.service.IJobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * @author wanxianbo
 * @description
 * @date 创建于 2022/9/6
 */
@Component
public class JobInfoMapperPipeline implements PageModelPipeline<JobInfo> {

    @Autowired
    private IJobInfoService jobInfoService;

    @Override
    public void process(JobInfo jobInfo, Task task) {
        jobInfoService.save(jobInfo);
    }
}

package com.wanxb.webmagic.jobinfo.controller;

import com.wanxb.webmagic.jobinfo.entity.JobInfo;
import com.wanxb.webmagic.jobinfo.pipeline.JobInfoMapperPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanxianbo
 * @since 2022-09-05
 */
@RestController
@RequestMapping("/jobinfo/job-info")
public class JobInfoController {

    @Autowired
    private JobInfoMapperPipeline jobInfoMapperPipeline;

    @PostMapping(value = "/start")
    public ResponseEntity<Boolean> startJobInfoTask() {
        OOSpider.create(Site.me().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36"), jobInfoMapperPipeline, JobInfo.class)
                .addUrl("https://my.oschina.net/flashsword")
                .thread(5)
                .run();

        return ResponseEntity.ok(Boolean.TRUE);
    }
}

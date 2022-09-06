package com.wanxb.webmagic.jobinfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxianbo
 * @since 2022-09-05
 */
@Data
@TableName("job_info")
@TargetUrl("https://www.liepin.com/job/*")
@HelpUrl("https://www.liepin.com/sojob/?dqs=020&curPage=\\d+")
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    @ExtractBy("//h1/text()")
    private String title;

    /**
     * 工资
     */
    @ExtractBy("//p[@class='job-item-title']/text()")
    private String salary;

    /**
     * 公司
     */
    @ExtractBy("//div[@class='title-info']/h3/a/text()")
    private String company;

    /**
     * 描述
     */
    @ExtractBy("//div[@class='content content-word']/allText()")
    private String description;

    /**
     * 来源
     */
    private String source;

    /**
     * url
     */
    @ExtractByUrl
    private String url;

    /**
     * md5加密url
     */
    private String urlMd5;

}

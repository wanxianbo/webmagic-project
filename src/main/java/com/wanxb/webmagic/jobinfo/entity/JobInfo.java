package com.wanxb.webmagic.jobinfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import us.codecraft.webmagic.model.annotation.ExtractBy;
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
@TargetUrl("https://my.oschina.net/flashsword/blog/\\d+")
@HelpUrl("https://my.oschina.net/flashsword")
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    @ExtractBy("//*[@id=\"mainScreen\"]/div/div[1]/div[1]/div[2]/div[1]/div/div/div/div[1]/h1/a/text()")
    private String title;

    /**
     * 工资
     */
    @ExtractBy("//*[@id=\"mainScreen\"]/div/div[1]/div[1]/div[2]/div[1]/div/div/div/div[1]/div/div/div[5]/text()")
    private String salary;

    /**
     * 公司
     */
    @ExtractBy("//*[@id=\"mainScreen\"]/div/div[1]/div[1]/div[2]/div[1]/div/div/div/div[1]/div/div/div[3]/a/text()")
    private String company;

    /**
     * 描述
     */
    @ExtractBy("//*[@id=\"mainScreen\"]/div/div[1]/div[1]/div[2]/div[1]/div/div/div/div[2]/div[1]/div[1]/div/allText()")
    private String description;

    /**
     * 来源
     */
    private String source = "oschina";

    /**
     * url
     */
//    @ExtractByUrl
    private String url;

    /**
     * md5加密url
     */
    private String urlMd5;

}

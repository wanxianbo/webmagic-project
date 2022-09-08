package com.wanxb.webmagic.jobinfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.JsonFilePageModelPipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

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
@TargetUrl("https://www.zhipin.com/job_detail/*")
//@HelpUrl("https://www.zhipin.com/web/geek/job?query=&city=100010000&position=100101&page=\\d+")
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    @ExtractBy("//*[@id=\"main\"]/div[1]/div/div/div[2]/div[2]/h1")
    private String title;

    /**
     * 工资
     */
    @ExtractBy("//*[@id=\"main\"]/div[1]/div/div/div[2]/div[2]/span/text()")
    private String salary;

    /**
     * 公司
     */
    @ExtractBy("//*[@id=\"main\"]/div[3]/div/div[1]/div[2]/div/a[2]/text()")
    private String company;

    /**
     * 描述
     */
    @ExtractBy("//*[@id=\"main\"]/div[3]/div/div[2]/div[2]/div[1]/div/text()")
    private String description;

    /**
     * 来源
     */
    private String source;

    /**
     * url
     */
//    @ExtractByUrl
    private String url;

    /**
     * md5加密url
     */
    private String urlMd5;

    public static void main(String[] args) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1", 7890)));
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader("C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        OOSpider.create(Site.me().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36"),
                        new ConsolePageModelPipeline(), JobInfo.class)
                .addUrl("https://www.zhipin.com/job_detail/5e1860d98800c9751XR52966EFZR.html?lid=7qbCMdquHZM.search.1&securityId=tUZYsfLLK8KWT-O1_jteXRkff7o8hVnSKKLQMsTkUpX9qQKEFA9ShGWWoEWhcBz3bV301qU7tqMne-12ilkTUvYT8uve3slzlpzWpitjltUYuj602g~~")
                .setDownloader(seleniumDownloader)
                .thread(5)
                .run();
    }

}

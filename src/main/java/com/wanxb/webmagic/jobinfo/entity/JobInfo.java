package com.wanxb.webmagic.jobinfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanxianbo
 * @since 2022-09-05
 */
@Getter
@Setter
@TableName("job_info")
@ApiModel(value = "JobInfo对象", description = "")
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("工资")
    private String salary;

    @ApiModelProperty("公司")
    private String company;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("来源")
    private String source;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("md5加密url")
    private String urlMd5;


}

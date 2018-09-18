package com.example.jwt.demo.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 谢霜
 * @since 2018-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "微信openId")
    private String openId;

    @ApiModelProperty(value = "头像链接")
    private String headUrl;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer state;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private LocalDateTime lastLoginTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

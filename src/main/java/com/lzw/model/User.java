package com.lzw.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@TableName("user")
public class User implements Serializable {

    @TableId(value = "id_", type = IdType.ID_WORKER)
    private Long id;

    @TableField("user_name")
    private String userName;

    private String password;

    private Integer status;

}

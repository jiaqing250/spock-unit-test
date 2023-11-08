package org.dbunit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * (Student)表实体类
 *
 * @author zfz
 * @since 2023-11-08
 */
@Data
@ToString
public class Student {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    private String gender;

    private String classInfo;

    private Date createdAt;


}


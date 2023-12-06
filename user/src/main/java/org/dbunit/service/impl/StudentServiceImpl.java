package org.dbunit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dbunit.dao.StudentMapper;
import org.dbunit.dto.StudentDTO;
import org.dbunit.entity.Student;
import org.dbunit.service.StudentService;
import org.dbunit.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * (Student)表服务实现类
 *
 * @author zfz
 * @since 2023-11-08
 */
@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public void save(StudentDTO dto) {
        if (dto.getId() == null) {
            this.save(BeanUtil.copyProperties(dto, Student.class));
        }
        if (dto.getAge() < 18) {
            throw new RuntimeException("未成年人不允许注册");
        }
        if (dto.getAge() > 60) {
            throw new RuntimeException("年龄超过60岁不允许注册");
        }
        if (!Objects.equals(dto.getGender(), "男") && !Objects.equals(dto.getGender(), "女")) {
            throw new RuntimeException("性别只能为男或女");
        }
        this.updateById(BeanUtil.copyProperties(dto, Student.class));
    }

    @Override
    public List<Student> all() {
        return this.lambdaQuery()
                .eq(Student::getName, UserUtils.getUserName())
                .list();
    }
}


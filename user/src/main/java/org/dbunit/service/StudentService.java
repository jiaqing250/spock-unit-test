package org.dbunit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.dbunit.dto.StudentDTO;
import org.dbunit.entity.Student;

import java.util.List;

/**
 * (Student)表服务接口
 *
 * @author zfz
 * @since 2023-11-08
 */
public interface StudentService extends IService<Student> {

    void save(StudentDTO dto);

    List<Student> all();

}


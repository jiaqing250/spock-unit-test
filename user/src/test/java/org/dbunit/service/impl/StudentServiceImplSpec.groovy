package org.dbunit.service.impl

import org.dbunit.dao.StudentMapper
import org.dbunit.dto.StudentDTO
import org.dbunit.supper.MapperUtil
import org.dbunit.supper.MyBaseSpec
import org.dbunit.utils.UserUtils
import org.mockito.MockedStatic
import org.mockito.Mockito
import spock.lang.Unroll

class StudentServiceImplSpec extends MyBaseSpec {


    private StudentServiceImpl studentServiceImpl

    def setup() {
        studentServiceImpl = new StudentServiceImpl();
        studentServiceImpl.baseMapper = MapperUtil.getMapper(StudentMapper)
        //Field entityClass of type Class - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
        //Field mapperClass of type Class - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    }
    /// region save 方法测试区域

    @Unroll
    def "单元测试: save --不会有异常"() {
        when:
        studentServiceImpl.save(new StudentDTO(id: id, name: name, age: age, gender: gender, classInfo: classInfo))

        then:
        notThrown(Exception.class)
        where:
        id   | name   | age | gender | classInfo
        null | "张三" | 18  | "男"   | "一班"
        1    | "张三" | 18  | "男"   | "一班"
        // todo check return value
    }

    @Unroll
    def "单元测试: save --会有异常"() {
        when:
        studentServiceImpl.save(new StudentDTO(id: id, name: name, age: age, gender: gender, classInfo: classInfo))

        then:
        thrown(RuntimeException.class)
        where:
        id   | name   | age | gender | classInfo
        null | "张三" | 17  | "男"   | "一班"
        null | "张三" | 61  | "男"   | "一班"
        null | "张三" | 60  | "人妖" | "一班"
        // todo check return value
    }
    ///endregion

    @Unroll
    def "单元测试: all --会有异常"() {
        when:
        studentServiceImpl.all()

        then:
        thrown(RuntimeException.class)

    }

    @Unroll
    def "单元测试: all --没有异常"() {
        when:
        MockedStatic<UserUtils> m = Mockito.mockStatic(UserUtils.class);
        m.when(UserUtils.&getUserName).thenReturn("张三");
        studentServiceImpl.all()

        then:
        notThrown(RuntimeException.class)

    }

}

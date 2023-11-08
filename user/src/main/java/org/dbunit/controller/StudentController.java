package org.dbunit.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dbunit.dto.StudentDTO;
import org.dbunit.service.StudentService;
import org.dbunit.vo.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Student)表控制层
 *
 * @author zfz
 * @since 2023-11-08
 */
@Api(tags = "模块")
@RestController
@RequestMapping("/student")
public class StudentController {
    /**
     * 服务对象
     */
    @Resource
    private StudentService studentService;

    @PostMapping("/save")
    @ApiOperation("商家账单数据导出接口")
    public JsonResult save(
            @RequestBody StudentDTO dto
    ) {
        studentService.save(dto);
        return JsonResult.success();
    }


}


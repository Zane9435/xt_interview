package com.xt.interview.controller;

import com.xt.interview.dto.DepartmentDTO;
import com.xt.interview.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理 Controller
 * 部门增删改查接口，路径前缀 /api/departments
 *
 * @author zhanghao
 * @date 2026-10-16
 */
@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取所有部门列表
     *
     * @return 部门列表
     */
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAll() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    /**
     * 根据ID获取部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    /**
     * 创建部门，@Valid 触发 DTO 上的长度校验
     *
     * @param dto 部门信息
     * @return 创建后的部门信息
     */
    @PostMapping
    public ResponseEntity<DepartmentDTO> create(@Valid @RequestBody DepartmentDTO dto) {
        return ResponseEntity.ok(departmentService.createDepartment(dto));
    }

    /**
     * 更新部门，@Valid 触发 DTO 上的长度校验
     *
     * @param id  部门ID
     * @param dto 部门信息
     * @return 更新后的部门信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> update(@PathVariable Long id, @Valid @RequestBody DepartmentDTO dto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, dto));
    }

    /**
     * 删除部门（软删除）
     *
     * @param id 部门ID
     * @return 空响应
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }
}

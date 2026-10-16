package com.xt.interview.service;

import com.xt.interview.dto.DepartmentDTO;

import java.util.List;

/**
 * 部门管理 Service 接口
 * 定义部门的增删改查业务
 *
 * @author zhanghao
 * @date 2026-10-16
 */
public interface DepartmentService {

    /**
     * 创建部门（校验部门编码唯一）
     *
     * @param dto 部门信息
     * @return 创建后的部门信息
     */
    DepartmentDTO createDepartment(DepartmentDTO dto);

    /**
     * 更新部门（校验部门编码唯一）
     *
     * @param id  部门ID
     * @param dto 部门信息
     * @return 更新后的部门信息
     */
    DepartmentDTO updateDepartment(Long id, DepartmentDTO dto);

    /**
     * 删除部门（软删除）
     *
     * @param id 部门ID
     */
    void deleteDepartment(Long id);

    /**
     * 根据ID查询部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    DepartmentDTO getDepartmentById(Long id);

    /**
     * 查询所有未删除的部门
     *
     * @return 部门列表
     */
    List<DepartmentDTO> getAllDepartments();
}

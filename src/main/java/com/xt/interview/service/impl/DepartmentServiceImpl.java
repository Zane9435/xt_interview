package com.xt.interview.service.impl;

import com.xt.interview.dto.DepartmentDTO;
import com.xt.interview.entity.Department;
import com.xt.interview.exception.BusinessException;
import com.xt.interview.repository.DepartmentRepository;
import com.xt.interview.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理 Service 实现
 * 实现部门的增删改查业务，删除为软删除，编码唯一性校验只针对未删除数据
 *
 * @author zhanghao
 * @date 2026-10-16
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private static final Integer NOT_DELETED = 0;
    private static final Integer DELETED = 1;

    @Autowired
    private DepartmentRepository departmentRepository;

    /** 创建部门，编码重复时抛出业务异常 */
    @Override
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        if (departmentRepository.existsByCodeAndDeleted(dto.getCode(), NOT_DELETED)) {
            throw new BusinessException("部门编码已存在");
        }
        Department department = new Department();
        department.setName(dto.getName());
        department.setCode(dto.getCode());
        department.setDescription(dto.getDescription());
        department.setStatus(dto.getStatus());
        department.setDeleted(NOT_DELETED);
        department = departmentRepository.save(department);
        return convertToDTO(department);
    }

    /** 更新部门，编码被其他部门占用时抛出业务异常 */
    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO dto) {
        Department department = departmentRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new BusinessException("部门不存在"));

        if (!department.getCode().equals(dto.getCode()) &&
            departmentRepository.existsByCodeAndDeleted(dto.getCode(), NOT_DELETED)) {
            throw new BusinessException("部门编码已存在");
        }

        department.setName(dto.getName());
        department.setCode(dto.getCode());
        department.setDescription(dto.getDescription());
        department.setStatus(dto.getStatus());
        department = departmentRepository.save(department);
        return convertToDTO(department);
    }

    /** 软删除部门，仅更新 deleted 标记 */
    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new BusinessException("部门不存在"));
        department.setDeleted(DELETED);
        departmentRepository.save(department);
    }

    /** 根据ID查询未删除的部门，不存在时抛出业务异常 */
    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new BusinessException("部门不存在"));
        return convertToDTO(department);
    }

    /** 查询所有未删除的部门 */
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findByDeleted(NOT_DELETED).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /** 实体转 DTO */
    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setCode(department.getCode());
        dto.setDescription(department.getDescription());
        dto.setStatus(department.getStatus());
        dto.setCreateTime(department.getCreateTime());
        dto.setUpdateTime(department.getUpdateTime());
        return dto;
    }
}

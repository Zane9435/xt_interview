package com.xt.interview.repository;

import com.xt.interview.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 部门 Repository，提供 sys_department 表的数据访问
 * 部门管理模块，按 deleted 标记过滤已删除数据
 *
 * @author zhanghao
 * @date 2026-10-16
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByDeleted(Integer deleted);
    Optional<Department> findByIdAndDeleted(Long id, Integer deleted);
    boolean existsByCodeAndDeleted(String code, Integer deleted);
}

package com.xt.interview.dto;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * 部门数据传输对象（DTO）
 * 部门管理模块，用于接口层的部门数据交互
 *
 * @author zhanghao
 * @date 2026-10-16
 */
public class DepartmentDTO {
    private Long id;
    /** 部门名称，最长100字符，对应 sys_department.name VARCHAR(100) */
    @Size(max = 100, message = "部门名称长度不能超过100个字符")
    private String name;
    /** 部门编码，最长50字符，对应 sys_department.code VARCHAR(50) */
    @Size(max = 50, message = "部门编码长度不能超过50个字符")
    private String code;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public DepartmentDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}

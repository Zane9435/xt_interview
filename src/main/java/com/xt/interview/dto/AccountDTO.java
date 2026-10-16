package com.xt.interview.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class AccountDTO {
    private Long id;
    /** 用户名，最长50字符，对应 sys_account.username VARCHAR(50) */
    @Size(max = 50, message = "用户名长度不能超过50个字符")
    private String username;
    /** 姓名，最长100字符，对应 sys_account.name VARCHAR(100) */
    @Size(max = 100, message = "姓名长度不能超过100个字符")
    private String name;
    private String gender;
    /** 手机号：1开头的11位数字，可空（^$ 放行空字符串） */
    @Pattern(regexp = "^$|^1\\d{10}$", message = "手机号需为1开头的11位数字")
    private String phone;
    /** 所属部门ID（任务二：用户-部门关联，可空） */
    private Long departmentId;
    /** 所属部门名称（任务二：后端关联查询回显，只读） */
    private String departmentName;
    private String password;
    private Integer roleLevel;
    private Integer status;
    private Integer isSuper;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public AccountDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getRoleLevel() { return roleLevel; }
    public void setRoleLevel(Integer roleLevel) { this.roleLevel = roleLevel; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Integer getIsSuper() { return isSuper; }
    public void setIsSuper(Integer isSuper) { this.isSuper = isSuper; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}

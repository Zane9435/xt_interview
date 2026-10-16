-- ==================== 账号表新增所属部门字段 ====================
-- 任务二：用户-部门关联
-- @author zhanghao
-- @date 2026-10-16
ALTER TABLE `sys_account`
    ADD COLUMN `department_id` BIGINT DEFAULT NULL COMMENT '所属部门ID' AFTER `phone`,
    ADD INDEX `idx_account_department_id` (`department_id`),
    ADD CONSTRAINT `fk_account_department` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`id`);

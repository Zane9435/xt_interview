package com.xt.interview.repository;

import com.xt.interview.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByDeleted(Integer deleted);
    Optional<Account> findByIdAndDeleted(Long id, Integer deleted);
    Optional<Account> findByUsernameAndDeleted(String username, Integer deleted);
    Optional<Account> findByUsernameAndPasswordAndDeleted(String username, String password, Integer deleted);
    boolean existsByUsernameAndDeleted(String username, Integer deleted);

    /**
     * 分页条件查询账号（任务三）
     * <p>支持用户名模糊搜索、按状态筛选、按权限级别筛选，条件为 null 时忽略该条件，只查未删除数据
     *
     * @param deleted   删除标记（传 NOT_DELETED）
     * @param username  用户名（模糊匹配，可为 null）
     * @param status    状态（可为 null）
     * @param roleLevel 权限级别（可为 null）
     * @param pageable  分页参数（page 从 0 开始）
     * @return 分页结果
     */
    @Query("SELECT a FROM Account a WHERE a.deleted = :deleted " +
            "AND (:username IS NULL OR a.username LIKE CONCAT('%', :username, '%')) " +
            "AND (:status IS NULL OR a.status = :status) " +
            "AND (:roleLevel IS NULL OR a.roleLevel = :roleLevel)")
    Page<Account> findPage(@Param("deleted") Integer deleted,
                           @Param("username") String username,
                           @Param("status") Integer status,
                           @Param("roleLevel") Integer roleLevel,
                           Pageable pageable);
}

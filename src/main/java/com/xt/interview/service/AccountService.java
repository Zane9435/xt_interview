package com.xt.interview.service;

import com.xt.interview.dto.AccountDTO;
import com.xt.interview.dto.PageResult;

import java.util.List;

public interface AccountService {

    AccountDTO createAccount(AccountDTO dto);

    AccountDTO updateAccount(Long id, AccountDTO dto);

    void deleteAccount(Long id);

    AccountDTO getAccountById(Long id);

    List<AccountDTO> getAllAccounts();

    /**
     * 分页条件查询账号列表（任务三）
     *
     * @param username  用户名（模糊搜索，可为 null）
     * @param status    状态（可为 null）
     * @param roleLevel 权限级别（可为 null）
     * @param page      页码，从 0 开始
     * @param size      每页条数
     * @return 分页结果
     */
    PageResult<AccountDTO> getPageAccounts(String username, Integer status, Integer roleLevel, int page, int size);

    AccountDTO login(String username, String password);
}

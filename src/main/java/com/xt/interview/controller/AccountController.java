package com.xt.interview.controller;

import com.xt.interview.dto.AccountDTO;
import com.xt.interview.dto.PageResult;
import com.xt.interview.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    /**
     * 分页条件查询账号（任务三）
     * <p>支持用户名模糊搜索、按状态筛选、按权限级别筛选，筛选条件均可选
     *
     * @param username  用户名（模糊搜索，可选）
     * @param status    状态（可选）
     * @param roleLevel 权限级别（可选）
     * @param page      页码，从 0 开始，默认 0
     * @param size      每页条数，默认 10
     * @return 分页结果 {list, totalElements, totalPages, currentPage, pageSize}
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<AccountDTO>> getPage(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer roleLevel,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(accountService.getPageAccounts(username, status, roleLevel, page, size));
    }

    /** 创建账号（任务二：支持关联部门），@Valid 触发 DTO 上的参数校验 */
    @PostMapping
    public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountDTO dto) {
        return ResponseEntity.ok(accountService.createAccount(dto));
    }

    /** 更新账号（任务二：支持关联部门），@Valid 触发 DTO 上的参数校验 */
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @Valid @RequestBody AccountDTO dto) {
        return ResponseEntity.ok(accountService.updateAccount(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}

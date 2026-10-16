package com.xt.interview.service.impl;

import com.xt.interview.dto.AccountDTO;
import com.xt.interview.dto.PageResult;
import com.xt.interview.entity.Account;
import com.xt.interview.entity.Department;
import com.xt.interview.exception.BusinessException;
import com.xt.interview.repository.AccountRepository;
import com.xt.interview.repository.DepartmentRepository;
import com.xt.interview.service.AccountService;
import com.xt.interview.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final Integer NOT_DELETED = 0;
    private static final Integer DELETED = 1;

    private static final String DEFAULT_PASSWORD = "123456";

    private static final String PHONE_PATTERN = "^1\\d{10}$";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public AccountDTO createAccount(AccountDTO dto) {
        validatePhone(dto.getPhone());
        if (accountRepository.existsByUsernameAndDeleted(dto.getUsername(), NOT_DELETED)) {
            throw new BusinessException("用户名已存在");
        }
        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setName(dto.getName());
        account.setGender(dto.getGender());
        account.setPhone(dto.getPhone());
        String password = (dto.getPassword() == null || dto.getPassword().isEmpty())
                ? DEFAULT_PASSWORD : dto.getPassword();
        account.setPassword(MD5Util.encode(password));
        account.setRoleLevel(dto.getRoleLevel());
        account.setStatus(dto.getStatus());
        account.setIsSuper(dto.getIsSuper());
        account.setDepartmentId(dto.getDepartmentId());
        account.setDeleted(NOT_DELETED);
        account = accountRepository.save(account);
        return convertToDTO(account);
    }

    @Override
    public AccountDTO updateAccount(Long id, AccountDTO dto) {
        validatePhone(dto.getPhone());
        Account account = accountRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new BusinessException("账号不存在"));

        if (!account.getUsername().equals(dto.getUsername()) &&
            accountRepository.existsByUsernameAndDeleted(dto.getUsername(), NOT_DELETED)) {
            throw new BusinessException("用户名已存在");
        }

        account.setUsername(dto.getUsername());
        account.setName(dto.getName());
        account.setGender(dto.getGender());
        account.setPhone(dto.getPhone());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            account.setPassword(MD5Util.encode(dto.getPassword()));
        }
        account.setRoleLevel(dto.getRoleLevel());
        account.setStatus(dto.getStatus());
        account.setIsSuper(dto.getIsSuper());
        account.setDepartmentId(dto.getDepartmentId());
        account = accountRepository.save(account);
        return convertToDTO(account);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new BusinessException("账号不存在"));
        account.setDeleted(DELETED);
        accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new BusinessException("账号不存在"));
        return convertToDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findByDeleted(NOT_DELETED).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 分页条件查询账号列表（任务三）
     * <p>用户名为空白时按无条件处理，按 id 倒序排列
     */
    @Override
    @Transactional(readOnly = true)
    public PageResult<AccountDTO> getPageAccounts(String username, Integer status, Integer roleLevel, int page, int size) {
        if (username != null && username.isBlank()) {
            username = null;
        }
        Page<Account> accountPage = accountRepository.findPage(
                NOT_DELETED, username, status, roleLevel,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

        List<AccountDTO> list = accountPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        PageResult<AccountDTO> result = new PageResult<>();
        result.setList(list);
        result.setTotalElements(accountPage.getTotalElements());
        result.setTotalPages(accountPage.getTotalPages());
        result.setCurrentPage(page);
        result.setPageSize(size);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO login(String username, String password) {
        Account account = accountRepository.findByUsernameAndPasswordAndDeleted(
                username, MD5Util.encode(password), NOT_DELETED)
                .orElseThrow(() -> new BusinessException("用户名或密码错误"));
        if (account.getStatus() != 1) {
            throw new BusinessException("账号已禁用");
        }
        return convertToDTO(account);
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setName(account.getName());
        dto.setGender(account.getGender());
        dto.setPhone(account.getPhone());
        dto.setRoleLevel(account.getRoleLevel());
        dto.setStatus(account.getStatus());
        dto.setIsSuper(account.getIsSuper());
        dto.setDepartmentId(account.getDepartmentId());
        dto.setDepartmentName(getDepartmentName(account.getDepartmentId()));
        dto.setCreateTime(account.getCreateTime());
        dto.setUpdateTime(account.getUpdateTime());
        return dto;
    }

    private void validatePhone(String phone) {
        if (phone != null && !phone.isBlank() && !phone.matches(PHONE_PATTERN)) {
            throw new BusinessException("手机号格式不正确，应为1开头的11位数字");
        }
    }

    /** 查询部门名称用于列表回显（任务二），部门不存在或已删除时返回 null */
    private String getDepartmentName(Long departmentId) {
        if (departmentId == null) {
            return null;
        }
        return departmentRepository.findByIdAndDeleted(departmentId, NOT_DELETED)
                .map(Department::getName)
                .orElse(null);
    }
}

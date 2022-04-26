package com.demo.projects.server.task.service.api.postgresql.mapper;

import com.demo.projects.server.task.service.api.model.UserDto;
import com.demo.projects.server.task.service.api.postgresql.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public UserDto convert(Account account) {
        UserDto userDto = new UserDto();
        userDto.setId(account.getId());
        userDto.setName(account.getName());
        userDto.setLogin(account.getLogin());
        userDto.setPassword(account.getPassword());
        return userDto;
    }

    public Account convert(UserDto projectDto) {
        Account account = new Account();
        account.setId(projectDto.getId());
        account.setName(projectDto.getName());
        account.setLogin(projectDto.getLogin());
        account.setPassword(projectDto.getPassword());
        return account;
    }
}

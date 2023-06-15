package org.hnxxxy.rg1b.service.user;

import org.hnxxxy.rg1b.domain.User;

public interface IUserService {

    User selectUserByUserId(Long userId);
}

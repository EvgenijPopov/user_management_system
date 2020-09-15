package popov.test.service;

import popov.test.UserAccountTransferClass;
import popov.test.entity.UserAccount;

import java.util.List;

public interface UserAccountService {
    // Service layer
    List<UserAccount> userAccountList(int pageId, int totalResults);

    UserAccount getUserAccountById(Integer id);

    void saveOrUpdateUserAccount(UserAccountTransferClass userAccount);

    UserAccount getUserAccountByUserName(String userName);

    void deleteUserAccount(Integer id);
}

package popov.test.dao;

import popov.test.entity.UserAccount;

import java.util.List;

public interface UserAccountDAO {
    // pattern Data Access Object

    List<UserAccount> userAccountList(int pageId, int totalResults);

    UserAccount getUserAccountById(Integer id);

    void saveOrUpdateUserAccount(UserAccount userAccount);

    UserAccount getUserAccountByUserName(String userName);

    void deleteUserAccount(Integer id);
}

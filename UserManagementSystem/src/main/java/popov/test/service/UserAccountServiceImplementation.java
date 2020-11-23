package popov.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popov.test.UserAccountTransferClass;
import popov.test.dao.UserAccountDAO;
import popov.test.entity.UserAccount;

import java.util.List;

@Service // define service bean (extends @Component)
public class UserAccountServiceImplementation implements UserAccountService {

    private final UserAccountDAO userAccountDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountServiceImplementation(UserAccountDAO userAccountDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userAccountDAO = userAccountDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Long getTotalCountOfUsers() {
        return userAccountDAO.getTotalCountOfUsers();
    }

    @Override
    @Transactional // annotation that takes over the transaction management mechanism for each method
    public List<UserAccount> userAccountList(int pageId, int totalResults) {
        return userAccountDAO.userAccountList(pageId, totalResults);
    }

    @Override
    @Transactional
    public UserAccount getUserAccountById(Integer id) {
        return userAccountDAO.getUserAccountById(id);
    }

    @Override
    @Transactional
    public void saveOrUpdateUserAccount(UserAccountTransferClass userTransferAccount) {
        userAccountDAO.saveOrUpdateUserAccount(unwrappedAccount(userTransferAccount));
    }

    @Override
    @Transactional
    public void deleteUserAccount(Integer id) {
        userAccountDAO.deleteUserAccount(id);
    }

    @Override
    @Transactional
    public UserAccount getUserAccountByUserName(String userName) {
        return userAccountDAO.getUserAccountByUserName(userName);
    }

    // this private method simplify object transformation between real entity object and transfer object
    private UserAccount unwrappedAccount(UserAccountTransferClass userTransferAccount) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userTransferAccount.getId());
        userAccount.setUserName(userTransferAccount.getUserName());
        // use password encryption right here
        userAccount.setPassword(passwordEncoder.encode(userTransferAccount.getPassword()));
        userAccount.setFirstName(userTransferAccount.getFirstName());
        userAccount.setLastName(userTransferAccount.getLastName());
        userAccount.setRole(userTransferAccount.getRole());
        userAccount.setStatus(userTransferAccount.getStatus());
        userAccount.setCreatedAt(userTransferAccount.getCreatedAt());
        return userAccount;
    }
}

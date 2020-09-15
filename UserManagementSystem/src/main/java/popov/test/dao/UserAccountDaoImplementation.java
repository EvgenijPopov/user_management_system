package popov.test.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import popov.test.entity.UserAccount;

import javax.persistence.NoResultException;
import java.util.List;

@Repository // define bean as repository (extends @Component)
public class UserAccountDaoImplementation implements UserAccountDAO {
    // DAO implementation

    private final SessionFactory sessionFactory;

    @Autowired
    public UserAccountDaoImplementation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    // this method returns List of UserAccounts limited by page id from user request and max results on this page
    public List<UserAccount> userAccountList(int pageId, int totalResults) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<UserAccount> query =
                currentSession.createQuery("FROM UserAccount", UserAccount.class); // HQL
        // Query has 2 useful methods for pagination
        query.setFirstResult(pageId); // first displayed result (starts with 0)
        query.setMaxResults(totalResults); // maximum displayed results
        List<UserAccount> list = query.getResultList(); // all results in List
        return list;
    }

    @Override
    // this method returns UserAccount get by Id (helpful for user details view and deleting user)
    public UserAccount getUserAccountById(Integer id) {
        Session currentSession = sessionFactory.getCurrentSession();
        UserAccount account = currentSession.get(UserAccount.class, id);
        return account;
    }

    @Override
    // simple ready to use method for saving or updating User Account
    public void saveOrUpdateUserAccount(UserAccount userAccount) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(userAccount);
    }

    @Override
    // this method provides User Account removal from DB
    public void deleteUserAccount(Integer id) {
        Session currentSession = sessionFactory.getCurrentSession();
        UserAccount userAccount = currentSession.get(UserAccount.class, id);
        currentSession.delete(userAccount);
    }

    @Override
    // this method is used for validation unique userName
    public UserAccount getUserAccountByUserName(String userName) {
        UserAccount account;
        // try to find UserAccount by userName
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            Query<UserAccount> query
                    = currentSession.createQuery
                    ("FROM UserAccount WHERE userName =:paramName", UserAccount.class);
            query.setParameter("paramName", userName); // bounded parameter
            account = query.getSingleResult();
        } catch (NoResultException exception) {
            account = null;
        }
        return account;
    }
}

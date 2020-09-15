package popov.test.validation;

import org.springframework.beans.factory.annotation.Autowired;
import popov.test.UserAccountTransferClass;
import popov.test.entity.UserAccount;
import popov.test.service.UserAccountService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// validator class <which annotation is used, object of validation>
public class UniqueAccountValidator implements ConstraintValidator<UniqueAccount, UserAccountTransferClass> {

    // we need some service methods
    private final UserAccountService userAccountService;

    @Autowired
    public UniqueAccountValidator(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public boolean isValid(UserAccountTransferClass userAccountTransferClass, ConstraintValidatorContext context) {
        // from given account
        String userName = userAccountTransferClass.getUserName(); // get name
        Integer userTransferId = userAccountTransferClass.getId(); // get Id (can be null)
        UserAccount userAccount = userAccountService.getUserAccountByUserName(userName); // we use get name for searching an existing account (can be null)
        if (userAccount == null) {
            return true; // 1st case - create account with new userName => account with this name doesn't exist (userAccount = null)
            // 5th case - edit account and enter new userName, also doesn't exist
            // further userAccount not null!
        } else if (userTransferId == null){
            return false; // 2nd case - try to create new account with non-unique userName (userAccount != null)
            // then at this stage userAccount doesn't have Id
        } else if(userTransferId.equals(userAccount.getId())){
            return true; // 3rd case - edit account but doesn't change userName,
            // when we editing account it already has id and queried account has id, and they are equal
        }
        return false; // 4th case - both account exist but have different id
    }
}

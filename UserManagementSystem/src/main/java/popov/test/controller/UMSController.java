package popov.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import popov.test.UserAccountTransferClass;
import popov.test.entity.UserAccount;
import popov.test.pagination.Page;
import popov.test.service.UserAccountService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller // define controller bean which handles all request from user (extends @Component)
@RequestMapping("/ums") // high-level mapping - add to all class methods specified prefix
public class UMSController {

    public static final int MAXIMUM_USERS_ON_PAGE = 8;

    private final UserAccountService userAccountService;

    @Autowired
    public UMSController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    // display list of users paginated
    @GetMapping("/users/{pageId}")
    public String listOfUsers(@PathVariable int pageId, Model model) {
        // @PathVariable indicates that pageId should be bound to a URI template {pageId} variable
        Long totalUsers = userAccountService.getTotalCountOfUsers();
        List<Page> pageRepository = createPageRepository(totalPages(totalUsers));
        model.addAttribute("pageRepository", pageRepository);
        List<UserAccount> userAccountList = userAccountService.userAccountList(firstDisplayedResult(pageId), MAXIMUM_USERS_ON_PAGE);
        model.addAttribute("listOfUsers", userAccountList); // named attribute - this "name" (used in View) mapped with List<UserAccount>
        return "list-of-users";
    }

    @GetMapping("/showDetails")
    public String listOfUserDetails(@RequestParam("userId") Integer userId, Model model) {
        // @RequestParam indicates that a method parameter userId should be bound to a web request parameter "userId"
        // by this parameter we get object and bound it with another model attribute
        UserAccount userAccount = userAccountService.getUserAccountById(userId);
        model.addAttribute("userDetails", userAccount);
        return "user-details";
    }

    @GetMapping("/new")
    public String addNewUser(Model model) {
        UserAccountTransferClass userAccount = new UserAccountTransferClass();
        model.addAttribute("userAccount", userAccount);
        return "add-user";
    }

    @PostMapping("/save")
    public String saveUserAccount(
            @Valid @ModelAttribute("userAccount") UserAccountTransferClass userAccountTransferClass,
            BindingResult bindingResult) {
        // before save we must check if fields have errors (don't passed validation)
        if (bindingResult.hasErrors()) {
            return "add-user";
        } else { // otherwise save transfer object as new entity object in DB
            userAccountService.saveOrUpdateUserAccount(userAccountTransferClass);
            return "redirect:/ums/users/1";
        }
    }

    @GetMapping("/edit")
    public String updateUserAccount(@RequestParam("userId") Integer id, Model model) {
        UserAccount userAccount = userAccountService.getUserAccountById(id);
        // by "userId" we get UserAccount - real entity object but for user we use TransferObject
        model.addAttribute("userAccount", wrappedAccount(userAccount));
        return "edit-user";
    }

    @PostMapping("/update")
    public String updateUserAccount(
            @Valid @ModelAttribute("userAccount") UserAccountTransferClass userAccountTransferClass,
            BindingResult bindingResult) {
        // the same things we do if want to update userAccount
        if (bindingResult.hasErrors()) {
            return "edit-user";
        } else {
            userAccountService.saveOrUpdateUserAccount(userAccountTransferClass);
            return "redirect:/ums/users/1";
        }
    }

    @GetMapping("/delete")
    public String deleteUserAccount(@RequestParam("userId") Integer id) {
        userAccountService.deleteUserAccount(id);
        return "redirect:/ums/users/1";
    }

    // this method make a proxy of UserAccount object (which will be validated)
    private UserAccountTransferClass wrappedAccount(UserAccount userAccount) {
        UserAccountTransferClass accountTransferClass = new UserAccountTransferClass();
        accountTransferClass.setId(userAccount.getId());
        accountTransferClass.setUserName(userAccount.getUserName());
        accountTransferClass.setPassword(userAccount.getPassword());
        accountTransferClass.setFirstName(userAccount.getFirstName());
        accountTransferClass.setLastName(userAccount.getLastName());
        accountTransferClass.setRole(userAccount.getRole());
        accountTransferClass.setStatus(userAccount.getStatus());
        accountTransferClass.setCreatedAt(userAccount.getCreatedAt());
        return accountTransferClass;
    }

    // method counts total number of Pages depend on total count of users that retrieve from DB
    private long totalPages(long totalUsers) {
        long fullPages = totalUsers / MAXIMUM_USERS_ON_PAGE;
        if (totalUsers % MAXIMUM_USERS_ON_PAGE == 0) {
            return fullPages;
        } else {
            return fullPages + 1;
        }
    }

    // method returns List of Pages filled by appropriate data
    private List<Page> createPageRepository(long totalPages) {
        List<Page> pageList = new ArrayList<>();
        for (int i = 1; i < totalPages + 1; i++) {
            Page singlePage = new Page(i);
            pageList.add(singlePage);
        }
        return pageList;
    }

    // calculating first result at next pages (8,16...)
    private int firstDisplayedResult(int currentPage) {
        int firstResult;
        if (currentPage == 1) {
            firstResult = 0;
        } else {
            firstResult = (currentPage - 1) * MAXIMUM_USERS_ON_PAGE;
        }
        return firstResult;
    }
}

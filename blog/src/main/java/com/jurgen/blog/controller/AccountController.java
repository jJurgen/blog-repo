package com.jurgen.blog.controller;

import com.jurgen.blog.domain.User;
import com.jurgen.blog.formbeans.EditProfileFormBean;
import com.jurgen.blog.formbeans.SignUpFormBean;
import com.jurgen.blog.service.UserService;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Resource(name = "userService")
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String editProfile(@Valid @ModelAttribute("editProfileFormBean") EditProfileFormBean editProfileFormBean,
            BindingResult result) {
        logger.info("request for editing profile");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!result.hasErrors()) {
            user.setAbout(editProfileFormBean.getAbout());
            userService.updateUser(user);            
            return "redirect:profile=" + user.getUsername();
        } else {
            logger.info("profile wasn't edited: There are " + result.getErrorCount() + " error(s) in data");
            return "editProfile";
        }
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String createAccount(@Valid @ModelAttribute("signUpForm") SignUpFormBean signUpForm,
            BindingResult result, Model model) {
        logger.info("request for signing up");
        boolean uniqueness = true;
        if (result.hasErrors()) {   
            logger.info("profile wasn't created: There are " + result.getErrorCount() + " error(s) in data");
            return "signUp";
        } else {
            if (userService.isUsernameExists(signUpForm.getUsername())) {
                model.addAttribute("usernameUniqueness", "Username already exist");
                uniqueness = false;
            }
            if (userService.isEmailExists(signUpForm.getEmail())) {
                model.addAttribute("emailUniqueness", "Email already exist");
                uniqueness = false;
            }
            if (!uniqueness) {
                logger.info("profile wasn't created: user are not unique");
                return "signUp";
            }
            userService.signUp(signUpForm);            
        }
        return "redirect:home?error";
    }

    @RequestMapping(value = "/removeProfile", method = RequestMethod.POST)
    public String removeProfile(@RequestParam("userId") Long userId) {
        logger.info("request for removing profile");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userService.hasUserRole(user, "ROLE_ADMIN")) {
            userService.removeUser(userId);
        }
        return "redirect:home";
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}

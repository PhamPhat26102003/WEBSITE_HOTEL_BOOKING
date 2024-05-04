package projectspring.admin.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspring.library.dto.AdminDto;
import projectspring.library.model.Admin;
import projectspring.library.service.IAdminService;

import java.security.Principal;

@Controller
public class AuthController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/index")
    public String indexPage(Model model, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        model.addAttribute("title", "Home");
        return "index";
    }
    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("title", "Login");
        return "login/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "login/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model){
        model.addAttribute("title", "Forgot password");
        return "login/forgot-password";
    }

    @PostMapping("/register-new")
    public String register(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("serverError", "Server error!!!");
                return "redirect:/register";
            }

            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);

            if(admin != null){
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("emailError", "Username has been registered!!!");
                return "redirect:/register";
            }

            if(adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.register(adminDto);
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("success", "Register success");
                return "redirect:/login";
            }
            else{
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addAttribute("passwordError", "Password is not the same!!!");
                return "redirect:/register";
            }
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("serverError", "Something wrong here, can not register!!!");
            return "redirect:/register";
        }
    }
}

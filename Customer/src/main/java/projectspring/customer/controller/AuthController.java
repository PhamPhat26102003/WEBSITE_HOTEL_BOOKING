package projectspring.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspring.library.dto.CustomerDto;
import projectspring.library.model.City;
import projectspring.library.model.Customer;
import projectspring.library.service.ICityService;
import projectspring.library.service.ICustomerService;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(){
        return "login/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        List<City> cities = cityService.findByActivated();
        model.addAttribute("cities", cities);
        model.addAttribute("customerDto", new CustomerDto());
        return "login/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model){
        return "login/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("username") String username,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 BindingResult bindingResult){
        Customer customer = customerService.findByUsername(username);
        if(customer == null){
            redirectAttributes.addFlashAttribute("failed", "Username not exist!!!");
            return "redirect:/forgot-password";
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerService.update(customer);
        model.addAttribute("customer", customer);
        redirectAttributes.addFlashAttribute("success", "Change password success");
        return "redirect:/forgot-password";
    }

    @PostMapping("/customer-new")
    public String register(@ModelAttribute("customerDto") CustomerDto customerDto,
                           Model model,
                           RedirectAttributes redirectAttributes,
                           BindingResult bindingResult){
        try{
            List<City> cities = cityService.findByActivated();
            if(bindingResult.hasErrors()){
                model.addAttribute("cities", cities);
                model.addAttribute("customerDto", customerDto);
                return "redirect:/register";
            }

            String username = customerDto.getUsername();
            Customer customer = customerService.findByUsername(username);
            if(customer != null){
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("cities", cities);
                redirectAttributes.addFlashAttribute("username", "Username has been registered!!!");
                return "redirect:/register";
            }

            if(customerDto.getPassword().equals(customerDto.getRepeatPass())){
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("cities", cities);
                redirectAttributes.addFlashAttribute("success", "Register success");
                return "redirect:/login";
            }
            else {
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("cities", cities);
                redirectAttributes.addFlashAttribute("password", "Password is not the same!!!");
                return "redirect:/register";
            }
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something wrong here, could not register!!!");
            return "redirect:/register";
        }
    }
}

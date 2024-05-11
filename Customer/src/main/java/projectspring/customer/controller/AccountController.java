package projectspring.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspring.library.model.City;
import projectspring.library.model.Customer;
import projectspring.library.service.ICityService;
import projectspring.library.service.ICustomerService;

import java.security.Principal;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICityService cityService;

    @GetMapping("/my-account")
    public String displayMyAccountPage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<City> cities = cityService.findByActivated();
        model.addAttribute("customer", customer);
        model.addAttribute("cities", cities);
        model.addAttribute("title", "My account");
        return "login/account";
    }

    @RequestMapping(value = "/update-account", method = {RequestMethod.PUT, RequestMethod.GET})
    public String updateMyAccount(Model model,
                                  RedirectAttributes redirectAttributes,
                                  Principal principal,
                                  @ModelAttribute("customer") Customer customer
                                  ){
        try{
            if(principal == null){
                return "redirect:/login";
            }
            Customer customerUpdate = customerService.update(customer);
            redirectAttributes.addFlashAttribute("success", "Update account success");
            model.addAttribute("customer", customerUpdate);
            return "redirect:/my-account";
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to update !!!");
            return "redirect:/my-account";
        }
    }
}

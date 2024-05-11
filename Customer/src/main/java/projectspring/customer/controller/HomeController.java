package projectspring.customer.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import projectspring.library.model.*;
import projectspring.library.service.ICategoryService;
import projectspring.library.service.ICityService;
import projectspring.library.service.ICustomerService;
import projectspring.library.service.IHotelService;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ICustomerService customerService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayHomePage(Model model, Principal principal, HttpSession session){
        List<City> cities = cityService.findByActivated();
        List<Category> categories = categoryService.findByActivated();
        model.addAttribute("cities", cities);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Home");
        if (principal != null) {
            Customer customer = customerService.findByUsername(principal.getName());
            session.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
        }else {
            session.removeAttribute("username");
        }
        return "index";
    }

    @GetMapping("/list-hotel")
    public String displayListHotelPage(Model model){
        List<Hotel> hotels = hotelService.findByActivated();
        List<Category> categories = categoryService.findByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("hotels", hotels);
        model.addAttribute("title", "Hotel");
        return "hotel/list-hotel";
    }
}

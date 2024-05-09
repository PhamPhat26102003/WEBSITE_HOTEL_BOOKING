package projectspring.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projectspring.library.model.Category;
import projectspring.library.model.City;
import projectspring.library.model.Hotel;
import projectspring.library.service.ICategoryService;
import projectspring.library.service.ICityService;
import projectspring.library.service.IHotelService;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private ICategoryService categoryService;
    @GetMapping("/")
    public String displayHomePage(Model model){
        List<City> cities = cityService.findByActivated();
        List<Category> categories = categoryService.findByActivated();
        model.addAttribute("cities", cities);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Home");
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

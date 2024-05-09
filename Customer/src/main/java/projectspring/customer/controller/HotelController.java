package projectspring.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projectspring.library.model.Category;
import projectspring.library.model.City;
import projectspring.library.model.Hotel;
import projectspring.library.service.ICategoryService;
import projectspring.library.service.ICityService;
import projectspring.library.service.IHotelService;

import java.util.List;

@Controller
public class HotelController {
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/list-hotel/city/{cityId}")
    public String displayHotelByCity(@PathVariable Long cityId, Model model){
        List<Category> categories = categoryService.findByActivated();
        City city = cityService.findById(cityId);
        List<Hotel> hotels = hotelService.findHotelByCity(city);
        model.addAttribute("hotels", hotels);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Hotel");
        return "list-hotel";
    }

    @GetMapping("/list-hotel/category/{categoryId}")
    public String displayHotelByCategory(@PathVariable Long categoryId, Model model){
        List<Category> categories = categoryService.findByActivated();
        Category category = categoryService.findById(categoryId);
        List<Hotel> hotels = hotelService.findHotelByCategory(category);
        model.addAttribute("hotels", hotels);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Hotel");
        return "list-hotel";
    }

}

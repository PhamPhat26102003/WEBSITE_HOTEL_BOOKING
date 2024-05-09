package projectspring.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspring.library.model.Category;
import projectspring.library.model.City;
import projectspring.library.model.Hotel;
import projectspring.library.service.ICategoryService;
import projectspring.library.service.ICityService;
import projectspring.library.service.IHotelService;
import projectspring.library.service.IStoreService;

import java.security.Principal;
import java.util.List;

@Controller
public class HotelController {
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private IStoreService storeService;

    @GetMapping("/hotels/{pageNo}")
    public String displayHotelPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
        Page<Hotel> hotels = hotelService.pageHotel(pageNo);
        model.addAttribute("hotels", hotels);
        model.addAttribute("size", hotels.getSize());
        model.addAttribute("totalPage", hotels.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("title", "Hotel");
        return "hotel/hotels";
    }

    @GetMapping("/add-hotel")
    public String displayAddNewCityPage(Model model, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
        List<City> cities = cityService.findByActivated();
        List<Category> categories = categoryService.findByActivated();
        model.addAttribute("cities", cities);
        model.addAttribute("categories", categories);
        model.addAttribute("hotel", new Hotel());
        model.addAttribute("title","New Hotel");
        return "hotel/addNew-hotel";
    }

    @PostMapping("/add-hotel")
    public String addNewHotel(@Validated Hotel hotel,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes){
        try{
            if(bindingResult.hasErrors() || hotel.getImage().isEmpty()){
            if(hotel.getImage().isEmpty()){
                bindingResult.rejectValue("image", "MultipartNotEmpty");
            }
            List<City> cities = cityService.findByActivated();
            List<Category> categories = categoryService.findByActivated();
            model.addAttribute("cities", cities);
            model.addAttribute("categories", categories);
            model.addAttribute("hotel", hotel);
            return "redirect:/add-hotel";
        }
            String filename = storeService.storeFile(hotel.getImage());
            hotel.setFilename(filename);
            hotelService.save(hotel);
            redirectAttributes.addFlashAttribute("success", "Add new hotel success");
            return "redirect:/hotels/0";
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add new hotel!!!");
            return "redirect:/add-hotel";
        }
    }

    @GetMapping("/update-hotel/{id}")
    public String displayUpdateHotelPage(@PathVariable("id") Long id, Model model, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
        Hotel hotel = hotelService.findById(id);
        List<City> cities = cityService.findByActivated();
        List<Category> categories = categoryService.findByActivated();
        model.addAttribute("hotel", hotel);
        model.addAttribute("cities", cities);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Update hotel");
        return "hotel/edit-hotel";
    }

    @PostMapping("/update-hotel/{id}")
    public String updateHotel(@Validated Hotel hotel,
                              RedirectAttributes redirectAttributes,
                              Model model,
                              BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                List<City> cities = cityService.findByActivated();
                List<Category> categories = categoryService.findByActivated();
                model.addAttribute("hotel", hotel);
                model.addAttribute("cities", cities);
                model.addAttribute("categories", categories);
                return "redirect:/edit-hotel";
            }
            hotelService.update(hotel);
            redirectAttributes.addFlashAttribute("success", "Update hotel success");
            return "redirect:/hotels/0";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to update!!!");
            return "redirect:/hotels/0";
        }
    }

    @RequestMapping(value = "enable-hotel/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enableHotelById(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            hotelService.enable(id);
            redirectAttributes.addFlashAttribute("success", "Enable success");
            return "redirect:/hotels/0";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to enable!!!");
            return "redirect:/hotels/0";
        }
    }

    @RequestMapping(value = "delete-hotel/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteHotelById(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            hotelService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Delete success");
            return "redirect:/hotels/0";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to delete!!!");
            return "redirect:/hotels/0";
        }
    }
}

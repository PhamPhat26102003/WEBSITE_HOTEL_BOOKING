package projectspring.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspring.library.dto.CustomerDto;
import projectspring.library.model.City;
import projectspring.library.service.ICityService;
import projectspring.library.service.IStoreService;

import java.security.Principal;
import java.util.List;

@Controller
public class CityController {
    @Autowired
    private ICityService cityService;
    @Autowired
    private IStoreService storeService;

    @GetMapping("/cities")
    public String displayCityPage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<City> cities = cityService.findAll();
        model.addAttribute("cities", cities);
        model.addAttribute("size", cities.size());
        model.addAttribute("cityNew", new City());
        model.addAttribute("title", "City");
        return "city/city";
    }

    @GetMapping("/add-city")
    public String displayAddNewCityPage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title", "Add new city");
        model.addAttribute("city", new City());
        return "city/addNew-city";
    }

    @PostMapping("/add-city")
    public String addNewCity(@Validated City city,
                             RedirectAttributes redirectAttributes,
                             Model model,
                             BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors() || city.getImage().isEmpty()){
                if(city.getImage().isEmpty()){
                    bindingResult.rejectValue("image", "MultipartNotEmpty");
                }
                model.addAttribute("city", city);
                return "redirect:/cities";
            }
            String filename = storeService.storeFile(city.getImage());
            city.setFilename(filename);
            cityService.save(city);
            redirectAttributes.addFlashAttribute("success","Add new city success");
            return "redirect:/cities";
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            dataIntegrityViolationException.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add new city!!!");
            return "redirect:/cities";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Something wrong here!!!");
            return "redirect:/cities";
        }
    }

    @GetMapping("/update-city/{id}")
    public String displayUpdateCityPage(@PathVariable("id") Long id, Model model){
        City city = cityService.findById(id);
        model.addAttribute("city", city);
        model.addAttribute("title", "Update city");
        return "city/edit-city";
    }

    @PostMapping("/update-city/{id}")
    public String updateCity(City city,RedirectAttributes redirectAttributes){
        try {
            cityService.update(city);
            redirectAttributes.addFlashAttribute("success", "Update success");
            return "redirect:/cities";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("failed", "Failed to update!!!");
            return "redirect:/cities";
        }
    }

    @RequestMapping(value = "/enable-city/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enableCityById(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            cityService.enable(id);
            redirectAttributes.addFlashAttribute("success", "Enable success");
            return "redirect:/cities";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to enable!!!");
            return "redirect:/cities";
        }
    }

    @RequestMapping(value = "/delete-city/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteCityById(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            cityService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Delete success");
            return "redirect:/cities";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to delete!!!");
            return "redirect:/cities";
        }
    }
}

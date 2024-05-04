package projectspring.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspring.library.model.Category;
import projectspring.library.service.ICategoryService;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public String categoryManagePage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("categoryNew", new Category());
        model.addAttribute("title", "Category");
        return "category/category";
    }

    @GetMapping("/add-category")
    public String displayAddNewCategoryPage(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("title", "Add category");
        return "category/addNew-category";
    }

    @PostMapping("/add-category")
    public String addNewCategory(@ModelAttribute("categoryNew") Category category, RedirectAttributes redirectAttributes){
        try{
            categoryService.save(category);
            redirectAttributes.addFlashAttribute("success", "Add category success");
            return "redirect:/categories";
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            redirectAttributes.addFlashAttribute("failed", "Failed to add new category!!!");
            return "redirect:/categories";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Something wrong here!!!");
            return "redirect:/categories";
        }
    }

    @GetMapping("/update-category/{id}")
    public String displayUpdateCategoryPage(@PathVariable Long id, Model model){
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        model.addAttribute("title", "Update category");
        return "category/edit-category";
    }

    @PostMapping("/update-category/{id}")
    public String updateCategory(@Validated Category category,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("category", category);
                return "category/edit-category";
            }
            categoryService.update(category);
            redirectAttributes.addFlashAttribute("success", "Update category success");
            return "redirect:/categories";
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to update!!");
            return "redirect:/categories";
        }
    }
}

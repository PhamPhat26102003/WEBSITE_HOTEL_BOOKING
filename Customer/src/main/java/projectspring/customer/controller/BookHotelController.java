package projectspring.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspring.library.model.BookHotel;
import projectspring.library.model.Booking;
import projectspring.library.model.Customer;
import projectspring.library.service.IBookHotelService;
import projectspring.library.service.ICustomerService;

import java.security.Principal;
import java.util.List;

@Controller
public class BookHotelController {
    @Autowired
    private IBookHotelService bookHotelService;
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/book-hotel")
    public String displayBookHotelPage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<BookHotel> bookHotels = customer.getBookHotels();
        model.addAttribute("bookHotels", bookHotels);
        model.addAttribute("title", "Book hotel");
        return "bookHotel/book-hotel";
    }

    @PostMapping("/book-hotel")
    public String bookHotel(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        Booking booking = customer.getBooking();
        BookHotel bookHotel = bookHotelService.save(booking);
        model.addAttribute("bookhotels", bookHotel);
        return "redirect:/book-hotel";
    }

    @RequestMapping(value = "/checked-out/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String checkedOut(@PathVariable("id") Long id,
                             Principal principal,
                             RedirectAttributes redirectAttributes){
        try{
            if(principal == null){
                return "redirect:/login";
            }
            bookHotelService.checkedOut(id);
            redirectAttributes.addFlashAttribute("success", "Checked out...");
            return "redirect:/book-hotel";
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Fall to check out!!!");
            return "redirect:/book-hotel";
        }
    }

    @RequestMapping(value = "/cancel/{id}")
    public String cancel(@PathVariable("id") Long id,
                         Principal principal,
                         RedirectAttributes redirectAttributes){
        try{
            if(principal == null){
                return "redirect:/login";
            }
            bookHotelService.cancel(id);
            redirectAttributes.addFlashAttribute("success", "Cancel success");
            return "redirect:/book-hotel";
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to cancel!!!");
            return "redirect:/book-hotel";
        }
    }
}

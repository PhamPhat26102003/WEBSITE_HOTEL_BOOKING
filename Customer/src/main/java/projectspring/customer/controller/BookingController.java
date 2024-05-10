package projectspring.customer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projectspring.library.model.BookItem;
import projectspring.library.model.Booking;
import projectspring.library.model.Customer;
import projectspring.library.model.Hotel;
import projectspring.library.service.IBookingService;
import projectspring.library.service.ICustomerService;
import projectspring.library.service.IHotelService;

import java.security.Principal;

@Controller
public class BookingController {
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/booking")
    public String displayBookingPage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        Booking booking = customer.getBooking();
        model.addAttribute("booking", booking);
        if(booking == null){
            model.addAttribute("check", "No hotel booked");
        }
        model.addAttribute("title", "Booking");
        return "bookHotel/booking";
    }

    @PostMapping("/booking-hotel")
    public String bookingHotel(Principal principal,
                               HttpServletRequest http,
                               @RequestParam("id") Long id,
                               @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantityRoom){
        if(principal == null){
            return "redirect:/login";
        }
        Hotel hotel = hotelService.findById(id);
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        Booking booking = bookingService.bookingHotel(hotel, quantityRoom, customer);
        return "redirect:" + http.getHeader("Referer");
    }
}

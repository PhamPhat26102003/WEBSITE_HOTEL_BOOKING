package projectspring.library.service;

import projectspring.library.model.Booking;
import projectspring.library.model.Customer;
import projectspring.library.model.Hotel;

public interface IBookingService {
    Booking bookingHotel(Hotel hotel, int quantityRoom, Customer customer);
    Booking updateBookingHotel(Hotel hotel, int quantityRoom, int quantityDay, Customer customer);
    Booking deleteBookingHotel(Hotel hotel, Customer customer);
}

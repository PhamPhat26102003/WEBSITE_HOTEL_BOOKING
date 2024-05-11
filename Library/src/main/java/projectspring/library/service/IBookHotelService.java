package projectspring.library.service;

import projectspring.library.model.BookHotel;
import projectspring.library.model.Booking;

public interface IBookHotelService {
    BookHotel save(Booking booking);
    BookHotel bookHotel(Long id);
    void cancel(Long id);
}

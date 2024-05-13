package projectspring.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspring.library.model.BookHotel;
import projectspring.library.model.BookHotelDetail;
import projectspring.library.model.BookItem;
import projectspring.library.model.Booking;
import projectspring.library.repository.IBookHotelDetailRepository;
import projectspring.library.repository.IBookHotelRepository;
import projectspring.library.repository.IBookItemRepository;
import projectspring.library.repository.IBookingRepository;
import projectspring.library.service.IBookHotelService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class BookHotelService implements IBookHotelService {

    @Autowired
    private IBookHotelRepository bookHotelRepository;
    @Autowired
    private IBookHotelDetailRepository bookHotelDetailRepository;
    @Autowired
    private IBookItemRepository bookItemRepository;
    @Autowired
    private IBookingRepository bookingRepository;
    @Override
    public BookHotel save(Booking booking) {
        BookHotel bookHotel = new BookHotel();
        bookHotel.setStatus("Completed...");
        bookHotel.setDateBook(new Date());
        bookHotel.setCustomer(booking.getCustomer());
        bookHotel.setTotalPrice(booking.getTotalPrice());
        bookHotel.setAccept(false);

        List<BookHotelDetail> bookHotelDetails = new ArrayList<>();

        for(BookItem item : booking.getBookItems()){
            BookHotelDetail bookHotelDetail = new BookHotelDetail();
            bookHotelDetail.setBookHotel(bookHotel);
            bookHotelDetail.setQuantityRoom(item.getQuantityRoom());
            bookHotelDetail.setQuantityDay(item.getQuantityDay());
            bookHotelDetail.setUnitPrice(item.getHotel().getCostPrice());
            bookHotelDetailRepository.save(bookHotelDetail);
            bookHotelDetails.add(bookHotelDetail);
            bookItemRepository.delete(item);
        }

        bookHotel.setBookHotelDetails(bookHotelDetails);
        booking.setBookItems(new HashSet<>());
        booking.setTotalPrice(0);
        bookingRepository.save(booking);
        bookHotelRepository.save(bookHotel);
        return bookHotel;
    }

    @Override
    public BookHotel checkedOut(Long id) {
        BookHotel bookHotel = bookHotelRepository.getById(id);
        bookHotel.setAccept(true);
        bookHotel.setCheckoutDate(new Date());
        bookHotel.setStatus("Checked out...");
        return bookHotelRepository.save(bookHotel);
    }

    @Override
    public void cancel(Long id) {
        bookHotelRepository.deleteById(id);
    }
}

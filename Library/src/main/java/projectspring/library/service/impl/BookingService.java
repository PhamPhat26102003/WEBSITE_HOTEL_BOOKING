package projectspring.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspring.library.model.BookItem;
import projectspring.library.model.Booking;
import projectspring.library.model.Customer;
import projectspring.library.model.Hotel;
import projectspring.library.repository.IBookItemRepository;
import projectspring.library.repository.IBookingRepository;
import projectspring.library.service.IBookingService;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private IBookingRepository bookingRepository;
    @Autowired
    private IBookItemRepository bookItemRepository;
    @Override
    public Booking bookingHotel(Hotel hotel, int quantityRoom, Customer customer) {
        Booking booking = customer.getBooking();

        if(booking == null){
            booking = new Booking();
        }

        Set<BookItem> bookItems = booking.getBookItems();
        BookItem bookItem = findBookItem(bookItems, hotel.getId());

        if(bookItems == null){
            bookItems = new HashSet<>();

            if(bookItem == null){
                bookItem = new BookItem();
                bookItem.setHotel(hotel);
                if(bookItem.getQuantityDay() > 1){
                    if(quantityRoom > 1){
                        bookItem.setTotalPrice(((bookItem.getQuantityDay() * hotel.getCostPrice())*quantityRoom)*0.7);
                    }
                }
                bookItem.setTotalPrice(bookItem.getQuantityDay() * hotel.getCostPrice());
                bookItem.setQuantityRoom(quantityRoom);
                bookItem.setQuantityDay(bookItem.getQuantityDay());
                bookItem.setBooking(booking);
                bookItems.add(bookItem);
                bookItemRepository.save(bookItem);
            }
        }else{
            if(bookItem == null){
                bookItem = new BookItem();
                bookItem.setHotel(hotel);
                if(bookItem.getQuantityDay() > 1){
                    if(quantityRoom > 1){
                        bookItem.setTotalPrice(((bookItem.getQuantityDay() * hotel.getCostPrice())*quantityRoom)*0.7);
                    }
                }
                bookItem.setTotalPrice(bookItem.getQuantityDay() * hotel.getCostPrice());
                bookItem.setQuantityRoom(quantityRoom);
                bookItem.setQuantityDay(bookItem.getQuantityDay());
                bookItem.setBooking(booking);
                bookItems.add(bookItem);
                bookItemRepository.save(bookItem);
            }else{
                bookItem.setQuantityRoom(bookItem.getQuantityRoom() + quantityRoom);
                if(bookItem.getQuantityDay() > 1){
                    if(quantityRoom > 1){
                        bookItem.setTotalPrice(bookItem.getTotalPrice() + ((bookItem.getQuantityDay() * hotel.getCostPrice()) * quantityRoom)*0.7);
                    }
                }
                bookItem.setTotalPrice(bookItem.getTotalPrice() + (bookItem.getQuantityDay() * hotel.getCostPrice()));
                bookItemRepository.save(bookItem);
            }
        }
        booking.setBookItems(bookItems);

        int totalRoom = totalRoom(booking.getBookItems());
        double totalPrice = totalPrice(booking.getBookItems());


        booking.setTotalHotel(totalRoom);
        booking.setTotalPrice(totalPrice);
        booking.setCustomer(customer);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBookingHotel(Hotel hotel, int quantityRoom, int quantityDay, Customer customer) {
        Booking booking =customer.getBooking();
        Set<BookItem> bookItems = booking.getBookItems();

        BookItem bookItem = findBookItem(bookItems, hotel.getId());
        bookItem.setQuantityDay(quantityDay);
        bookItem.setQuantityRoom(quantityRoom);
        if(quantityDay > 1){
            if(quantityRoom > 1){
                bookItem.setTotalPrice(bookItem.getTotalPrice() + ((quantityDay * hotel.getCostPrice()) * quantityRoom)*0.7);
            }
        }
        bookItem.setTotalPrice(bookItem.getTotalPrice() + (quantityDay * hotel.getCostPrice()));
        bookItemRepository.save(bookItem);

        int totalRoom = totalRoom(bookItems);
        int totalDay = totalDay(bookItems);
        double totalPrice = totalPrice(bookItems);

        booking.setTotalHotel(totalRoom);
        booking.setTotalDay(totalDay);
        booking.setTotalPrice(totalPrice);
        return bookingRepository.save(booking);
    }

    private BookItem findBookItem(Set<BookItem> bookItems, Long id){
        if(bookItems == null){
            return null;
        }
        BookItem bookItem = null;
        for(BookItem item : bookItems){
            if(item.getHotel().getId() == id){
                bookItem = item;
            }
        }
        return bookItem;
    }

    private int totalRoom(Set<BookItem> bookItems){
        int totalRoom = 0;
        for (BookItem item : bookItems){
            totalRoom += item.getQuantityRoom();
        }
        return totalRoom;
    }

    private int totalDay(Set<BookItem> bookItems){
        int totalDay = 0;
        for (BookItem item : bookItems){
            totalDay += item.getQuantityDay();
        }
        return totalDay;
    }

    private double totalPrice(Set<BookItem> bookItems){
        double totalPrice = 0.0;
        for(BookItem item : bookItems){
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }
}

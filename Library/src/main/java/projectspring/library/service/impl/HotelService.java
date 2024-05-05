package projectspring.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspring.library.model.Hotel;
import projectspring.library.repository.IHotelRepository;
import projectspring.library.service.IHotelService;
import projectspring.library.service.IStoreService;

import java.util.List;

@Service
public class HotelService implements IHotelService {
    @Autowired
    private IHotelRepository hotelRepository;
    @Autowired
    private IStoreService storeService;
    @Override
    public List<Hotel> findAll(String keyword) {
        if(keyword != null){
            return hotelRepository.findAll(keyword);
        }
        return hotelRepository.findAll();
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.getById(id);
    }

    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Hotel hotel) {
        try{
            Hotel hotelUpdate = hotelRepository.getById(hotel.getId());
            hotelUpdate.setName(hotel.getName());
            hotelUpdate.setQuantityRoom(hotel.getQuantityRoom());
            hotelUpdate.setStatus(hotel.getStatus());
            hotelUpdate.setCostPrice(hotel.getCostPrice());
            hotelUpdate.setAddress(hotel.getAddress());
            hotelUpdate.setDescription(hotel.getDescription());
            hotelUpdate.setCity(hotel.getCity());
            hotelUpdate.setCategory(hotel.getCategory());

            if(!hotel.getImage().isEmpty()){
                storeService.deleteFile(hotelUpdate.getFilename());
                String filename = storeService.storeFile(hotel.getImage());
                hotelUpdate.setFilename(filename);
            }
            return hotelRepository.save(hotelUpdate);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void enable(Long id) {
        Hotel hotel = hotelRepository.getById(id);
        hotel.set_activated(true);
        hotel.set_deleted(false);
        hotelRepository.save(hotel);
    }

    @Override
    public void delete(Long id) {
        Hotel hotel = hotelRepository.getById(id);
        hotel.set_deleted(true);
        hotel.set_activated(false);
        hotelRepository.save(hotel);
    }
}

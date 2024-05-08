package projectspring.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspring.library.model.City;
import projectspring.library.repository.ICityRepositoty;
import projectspring.library.service.ICityService;
import projectspring.library.service.IStoreService;

import java.util.List;

@Service
public class CityService implements ICityService {
    @Autowired
    private ICityRepositoty cityRepositoty;
    @Autowired
    private IStoreService storeService;
    @Override
    public List<City> findAll() {
        return cityRepositoty.findAll();
    }

    @Override
    public List<City> findByActivated() {
        return cityRepositoty.findByActivated();
    }

    @Override
    public City findById(Long id) {
        return cityRepositoty.getById(id);
    }

    @Override
    public City save(City city) {
        return cityRepositoty.save(city);
    }

    @Override
    public City update(City city) {
        try{
            City cityUpdate = cityRepositoty.getById(city.getId());
            cityUpdate.setName(city.getName());
            cityUpdate.setProperties(city.getProperties());
            cityUpdate.set_activated(city.is_activated());
            cityUpdate.set_deleted(city.is_deleted());
            if(!city.getImage().isEmpty()){
                storeService.deleteFile(cityUpdate.getFilename());
                String filename = storeService.storeFile(city.getImage());
                cityUpdate.setFilename(filename);
            }
            return cityRepositoty.save(cityUpdate);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void enable(Long id) {
        City city = cityRepositoty.getById(id);
        city.set_activated(true);
        city.set_deleted(false);
        cityRepositoty.save(city);
    }

    @Override
    public void delete(Long id) {
        City city = cityRepositoty.getById(id);
        city.set_deleted(true);
        city.set_activated(false);
        cityRepositoty.save(city);
    }
}

package uz.pdp.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.HotelApplication;
import uz.pdp.hotel.entity.Hotel;
import uz.pdp.hotel.repository.HotelRepository;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> get() {
        List<Hotel> hotelList = hotelRepository.findAll();
        return hotelList;
    }

    @PostMapping
    public String save(@RequestBody Hotel hotel) {
        boolean byName = hotelRepository.existsByName(hotel.getName());
        if (byName) return "already exist";
        hotelRepository.save(hotel);
        return "saved";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        try {
            hotelRepository.deleteById(id);
            return " deleted";

        } catch (Exception e) {
            return "not deleting";
        }


    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody Hotel hotel) {
        if (!hotelRepository.existsById(id)) {
            return "not found id";
        }
        Hotel hotel1 = new Hotel(null, hotel.getName());
        hotelRepository.save(hotel1);
        return "editing";
    }

}

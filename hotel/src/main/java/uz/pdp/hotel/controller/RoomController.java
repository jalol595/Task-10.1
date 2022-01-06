package uz.pdp.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.entity.Hotel;
import uz.pdp.hotel.entity.Room;
import uz.pdp.hotel.repository.HotelRepository;
import uz.pdp.hotel.repository.RoomRepository;
import uz.pdp.hotel.transfer.RoomDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;


    @GetMapping("/{hotelId}")
    public Page<Room> getByHotelId(@PathVariable Integer hotelId, @RequestParam int page){
        if (roomRepository.existsById(hotelId)){
            Pageable pageable= PageRequest.of(page, 10);
            Page<Room> roomPage = roomRepository.getAllByHotelId(hotelId, pageable);
            return roomPage;
        }
      return null;
    }


    @GetMapping
    public List<Room> get() {
        List<Room> roomList = roomRepository.findAll();
        return roomList;
    }

    @PostMapping
    public String save(@RequestBody RoomDto roomDto) {
        boolean size = roomRepository.existsByNumberAndFloorAndSize(roomDto.getNumber(), roomDto.getFloor(), roomDto.getSize());
        boolean id = hotelRepository.existsById(roomDto.getHotelId());
        if (size && id) return "already exist";

        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) {
            return "not found hotel";
        }

        Hotel hotel = optionalHotel.get();

        room.setHotel(hotel);
        roomRepository.save(room);
        return "saved";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        try {
            roomRepository.deleteById(id);
            return "deleted";
        } catch (Exception e) {
            return "eror deleting";
        }
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        if (!roomRepository.existsById(id)) {
            return "not found id";
        }


        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) return "not found hotel";

        Hotel hotel = optionalHotel.get();

        room.setHotel(hotel);
        roomRepository.save(room);
        return "editing";

    }

}

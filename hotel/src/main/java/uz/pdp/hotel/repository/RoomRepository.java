package uz.pdp.hotel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hotel.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByNumberAndFloorAndSize(Integer number, String floor, double size);
//   getAllByHotelId(Pageable pageable);

    Page<Room>  getAllByHotelId(Integer hotelId, Pageable pageable);
}

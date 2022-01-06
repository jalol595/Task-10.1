package uz.pdp.hotel.transfer;

import lombok.Data;

@Data
public class RoomDto {

    private Integer number;
    private String floor;
    private double size;
    private Integer hotelId;
}

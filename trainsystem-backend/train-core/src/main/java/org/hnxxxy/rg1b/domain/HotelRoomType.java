package org.hnxxxy.rg1b.domain;

import lombok.Data;

@Data
public class HotelRoomType {
    private String roomTypeId;
    private String roomType;
    private Double typePrice;
    private String hotelId;
}

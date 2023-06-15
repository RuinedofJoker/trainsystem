package org.hnxxxy.rg1b.service.hotel;

import org.hnxxxy.rg1b.common.PageVo;
import org.hnxxxy.rg1b.domain.Hotel;
import org.hnxxxy.rg1b.domain.HotelRoomType;
import org.hnxxxy.rg1b.domain.vo.HotelVo;

import java.util.List;

public interface IHotelService {
    PageVo selectHotelByCityId(Integer cityId, int pageNo, int pageSize);

    HotelVo selectHotelDetail(String hotelId);

    List<HotelRoomType> selectRoomTypeByHotelId(String hotelId);
}

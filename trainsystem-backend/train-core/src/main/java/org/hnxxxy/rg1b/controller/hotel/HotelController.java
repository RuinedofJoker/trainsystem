package org.hnxxxy.rg1b.controller.hotel;

import org.hnxxxy.rg1b.common.PageVo;
import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.domain.Hotel;
import org.hnxxxy.rg1b.domain.HotelRoomType;
import org.hnxxxy.rg1b.domain.vo.HotelVo;
import org.hnxxxy.rg1b.service.hotel.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    /**
     * 根据城市id查询酒店
     * @param cityId 城市id
     */
    @GetMapping("/list/{cityId}")
    public ResultVo getHotel(@PathVariable Integer cityId, @RequestParam int pageNo, @RequestParam int pageSize){
        PageVo<Hotel> hotelPage = hotelService.selectHotelByCityId(cityId, pageNo, pageSize);
        return ResultVo.success(hotelPage);
    }

    /**
     * 根据酒店id获取酒店详细信息
     * @param hotelId 酒店ID
     */
    @GetMapping("/detail/{hotelId}")
    public ResultVo getHotelDetail(@PathVariable String hotelId){
        HotelVo hotel = hotelService.selectHotelDetail(hotelId);

        return ResultVo.success(hotel);
    }

    /**
     * 根据酒店id获取酒店房间信息
     * @param hotelId
     * @return
     */
    @GetMapping("/roomtype/{hotelId}")
    public ResultVo getHotelRoomType(@PathVariable String hotelId) {
        List<HotelRoomType> roomTypes = hotelService.selectRoomTypeByHotelId(hotelId);
        return ResultVo.success(roomTypes);
    }
}

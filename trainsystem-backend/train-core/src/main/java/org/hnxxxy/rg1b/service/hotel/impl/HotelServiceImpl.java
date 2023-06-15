package org.hnxxxy.rg1b.service.hotel.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hnxxxy.rg1b.common.PageVo;
import org.hnxxxy.rg1b.domain.City;
import org.hnxxxy.rg1b.domain.Hotel;
import org.hnxxxy.rg1b.domain.HotelRoomType;
import org.hnxxxy.rg1b.domain.StorageFile;
import org.hnxxxy.rg1b.domain.vo.HotelVo;
import org.hnxxxy.rg1b.mapper.CityMapper;
import org.hnxxxy.rg1b.mapper.HotelMapper;
import org.hnxxxy.rg1b.mapper.HotelRoomTypeMapper;
import org.hnxxxy.rg1b.mapper.StorageFileMapper;
import org.hnxxxy.rg1b.service.file.IFileService;
import org.hnxxxy.rg1b.service.hotel.IHotelService;
import org.hnxxxy.rg1b.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceImpl implements IHotelService {

    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private IFileService fileService;
    @Autowired
    private HotelRoomTypeMapper roomTypeMapper;

    /**
     * 根据城市名查询酒店
     *
     * @param cityId 城市id
     * @return 酒店
     */
    @Override
    public PageVo selectHotelByCityId(Integer cityId, int pageNo, int pageSize) {

        List<Hotel> hotels = hotelMapper.selectHotelByCityId(cityId, pageNo, pageSize);
        int totalNum = hotelMapper.selectCountByCityId(cityId);
        PageVo page = new PageVo(totalNum, pageNo, pageSize, hotels);
        return page;
    }

    /**
     * 根据酒店ID获取酒店详细信息 同时回显操作
     * @param hotelId 酒店ID
     * @return 回显数据
     */
    @Override
    public HotelVo selectHotelDetail(String hotelId) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(hotelId);

        LambdaQueryWrapper<Hotel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Hotel::getHotelId, hotel.getHotelId());
        hotel = hotelMapper.selectOne(queryWrapper);

        HotelVo hotelVo = new HotelVo();
        BeanUtils.copyProperties(hotel, hotelVo);

        String content = null;
        try {
            content = fileService.getStorageFile(fileService.selectFileInfoById(hotel.getHotelPoliciesId()).getContent());
        } catch (FileNotFoundException e) {
            return null;
        }

        hotelVo.setHotelPolicies(content);

        return hotelVo;
    }

    @Override
    public List<HotelRoomType> selectRoomTypeByHotelId(String hotelId) {

        LambdaQueryWrapper<HotelRoomType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HotelRoomType::getHotelId, hotelId);
        List<HotelRoomType> roomTypes = roomTypeMapper.selectList(queryWrapper);
        return roomTypes;
    }
}

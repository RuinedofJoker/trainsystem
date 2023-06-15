package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.Hotel;

import java.util.List;


@Mapper
public interface HotelMapper extends BaseMapper<Hotel> {

    List<Hotel> selectHotelByCityId(@Param("cityId") int cityId,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    int selectCountByCityId(@Param("cityId") int cityId);
}


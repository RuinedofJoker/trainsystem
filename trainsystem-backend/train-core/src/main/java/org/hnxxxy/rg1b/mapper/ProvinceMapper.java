package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.hnxxxy.rg1b.domain.City;
import org.hnxxxy.rg1b.domain.Province;

import java.util.List;

@Mapper
public interface ProvinceMapper extends BaseMapper<Province> {
}

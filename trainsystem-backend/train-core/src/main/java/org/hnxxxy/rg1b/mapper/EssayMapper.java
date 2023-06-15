package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.Essay;

import java.util.List;

@Mapper
public interface EssayMapper extends BaseMapper<Essay> {
    //通过景点id获取景点的游记
    List<Essay> getEssayByAttractionsId(@Param("attractionsId")Integer attractionsId);
    List<Essay> selectRecommendEssay(@Param("num")int num);
}

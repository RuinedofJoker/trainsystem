package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.Picture;

import java.util.List;

@Mapper
public interface PictureMapper extends BaseMapper<Picture> {
    //通过attractionsId获取景点详细图片信息
    List<Picture> getDetailPictureByAttractionsId(@Param("attractionsId") Integer attractionsId);

    //通过attractionsId获取景点推荐图片信息
    String getRecommendPictureByAttractionsId(@Param("attractionsId") Integer attractionsId);

    //通过englishName获取城市大图
    String getCityPictureByEnglishName(@Param("englishName") String englishName);

    //通过attractionsId获取景点略缩图的fileId
    String getFileIdByAttractionsId(@Param("attractionsId") Integer attractionsId);

    //通过attractionsId获取景点封面图片的fileId
    String getCoverFileByAttractionsId(@Param("attractionsId") Integer attractionsId);
}

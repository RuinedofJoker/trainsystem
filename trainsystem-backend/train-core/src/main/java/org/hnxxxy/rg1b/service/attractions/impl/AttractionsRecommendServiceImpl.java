package org.hnxxxy.rg1b.service.attractions.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.hnxxxy.rg1b.domain.AttractionsRecommend;
import org.hnxxxy.rg1b.domain.Picture;
import org.hnxxxy.rg1b.domain.dto.AttractionsRecommendDetail;
import org.hnxxxy.rg1b.domain.vo.AttractionsRecommendDetailVo;
import org.hnxxxy.rg1b.mapper.AttractionsRecommendMapper;
import org.hnxxxy.rg1b.mapper.CityMapper;
import org.hnxxxy.rg1b.mapper.PictureMapper;
import org.hnxxxy.rg1b.service.attractions.AttractionsRecommendService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttractionsRecommendServiceImpl implements AttractionsRecommendService {

    @Resource
    private AttractionsRecommendMapper attractionsRecommendMapper;

    @Resource
    private PictureMapper pictureMapper;

    @Resource
    private CityMapper cityMapper;

    @Override
    public AttractionsRecommendDetailVo getCityRecommendPlaceAndOther(String englishName) {
        AttractionsRecommendDetailVo attractionsRecommendDetailVo = new AttractionsRecommendDetailVo();
        attractionsRecommendDetailVo.setEnglishName(englishName);
        attractionsRecommendDetailVo.setCityId(Integer.parseInt(cityMapper.getCityIdByEnglishName(englishName)));
        try {
            attractionsRecommendDetailVo.setCityPictureFileId(pictureMapper.getCityPictureByEnglishName(englishName));
        }catch (Exception e){
        }
        //创建链表存储最终
        List<AttractionsRecommendDetail> attractionsRecommendDetailList = new ArrayList<>();

        //获取所有推荐景点信息
        List<AttractionsRecommend> attractionsRecommendList = attractionsRecommendMapper.getCityRecommendPlace(englishName);
        for (AttractionsRecommend attractionsRecommend : attractionsRecommendList){
            AttractionsRecommendDetail attractionsRecommendDetail = new AttractionsRecommendDetail();
            attractionsRecommendDetail.setAttractionsName(attractionsRecommend.getAttractionsName());
            attractionsRecommendDetail.setAppraiseScore(attractionsRecommend.getAppraiseScore());
            attractionsRecommendDetail.setComments(attractionsRecommend.getComments());
            attractionsRecommendDetail.setSummary(attractionsRecommend.getSummary());
            attractionsRecommendDetail.setAttractionsId(attractionsRecommend.getAttractionsId());
            LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Picture::getOwnedId, attractionsRecommend.getAttractionsId());
            queryWrapper.eq(Picture::getPictureType, 5);
            try {
                attractionsRecommendDetail.setFileId(pictureMapper.selectOne(queryWrapper).getPictureId());
            }catch (Exception e){
            }
            attractionsRecommendDetailList.add(attractionsRecommendDetail);
        }
        attractionsRecommendDetailVo.setAttractionsRecommendDetail(attractionsRecommendDetailList);
        return attractionsRecommendDetailVo;
    }
}

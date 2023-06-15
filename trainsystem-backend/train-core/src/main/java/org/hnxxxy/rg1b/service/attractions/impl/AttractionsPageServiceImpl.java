package org.hnxxxy.rg1b.service.attractions.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hnxxxy.rg1b.domain.dto.AttractionsPage;
import org.hnxxxy.rg1b.domain.vo.AttractionsPageVo;
import org.hnxxxy.rg1b.mapper.AttractionsPageMapper;
import org.hnxxxy.rg1b.mapper.CityMapper;
import org.hnxxxy.rg1b.mapper.PictureMapper;
import org.hnxxxy.rg1b.mapper.StorageFileMapper;
import org.hnxxxy.rg1b.service.attractions.AttractionsPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttractionsPageServiceImpl implements AttractionsPageService {

    @Resource
    private AttractionsPageMapper attractionsPageMapper;

    @Resource
    private CityMapper cityMapper;

    @Resource
    private PictureMapper pictureMapper;


    @Override
    public AttractionsPageVo getAttractionsPageVoByEnglishNameAndPage(String englishName, Integer page1) {
        AttractionsPageVo attractionsPageVo = new AttractionsPageVo();
        attractionsPageVo.setEnglishName(englishName);
        Integer cityId = Integer.parseInt(cityMapper.getCityIdByEnglishName(englishName));
        attractionsPageVo.setCityId(cityId);
        //开启分页
        Page<Object> page = PageHelper.startPage(page1,10);
        List<AttractionsPage> attractionsPageList = attractionsPageMapper.getAttractionsPageByEnglishName(cityId);
        PageInfo<AttractionsPage> pageInfo = new PageInfo<>(attractionsPageList,5);
        //获取图片信息
        for (AttractionsPage attractionsPage : pageInfo.getList()) {
            try {
                attractionsPage.setFileId(pictureMapper.getFileIdByAttractionsId(attractionsPage.getAttractionsId()));
            }catch (Exception e){
            }
        }

        attractionsPageVo.setPageInfo(pageInfo);
        return attractionsPageVo;
    }
}

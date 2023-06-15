package org.hnxxxy.rg1b.service.attractions.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hnxxxy.rg1b.domain.Attractions;
import org.hnxxxy.rg1b.domain.Essay;
import org.hnxxxy.rg1b.domain.Picture;
import org.hnxxxy.rg1b.domain.StorageFile;
import org.hnxxxy.rg1b.domain.dto.AttractionsCityNameAndUrl;
import org.hnxxxy.rg1b.domain.dto.AttractionsDetail;
import org.hnxxxy.rg1b.domain.vo.AttractionsDetailVo;
import org.hnxxxy.rg1b.mapper.*;
import org.hnxxxy.rg1b.service.attractions.AttractionsService;
import org.hnxxxy.rg1b.service.file.IFileService;
import org.hnxxxy.rg1b.utils.fileutils.FileRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttractionsServiceImpl implements AttractionsService {

    @Resource
    private AttractionsMapper attractionsMapper;
    @Resource
    private TagsMapper tagsMapper;
    @Resource
    private EssayMapper essayMapper;
    @Resource
    private StorageFileMapper storageFileMapper;
    @Autowired
    private IFileService fileService;
    @Autowired
    private PictureMapper pictureMapper;
    @Resource
    private CityMapper cityMapper;

    @Override
    public AttractionsDetailVo getAttractionsDetail(Integer attractionsId) {
        AttractionsDetailVo attractionsDetailVo = new AttractionsDetailVo();
        Attractions attractions = attractionsMapper.getAttractionsByAttractionsId(attractionsId);

        attractionsDetailVo.setEnglishName(cityMapper.getEnglishNameByCityId(attractions.getCityId()));

        AttractionsDetail attractionsDetail = new AttractionsDetail();
        attractionsDetail.setAttractionsId(attractions.getAttractionsId());
        attractionsDetail.setAttractionsName(attractions.getAttractionsName());
        attractionsDetail.setPrice(attractions.getPrice());
        attractionsDetail.setHeat(attractions.getHeat());
        attractionsDetail.setAppraiseScore(attractions.getAppraiseScore());
        attractionsDetail.setAddress(attractions.getAddress());
        attractionsDetail.setOpeningHours(attractions.getOpeningHours());
        attractionsDetail.setPhoneNumber(attractions.getPhoneNumber());

        StorageFile htmlContent = storageFileMapper.getStorageFileByFileId(attractions.getFileId());
        try {
            attractionsDetail.setDetail(fileService.getStorageFile(htmlContent.getContent()));
        } catch (FileNotFoundException e) {
        }

        attractionsDetail.setTags(tagsMapper.getTagsByAttractionsId(attractionsId));

        List<StorageFile> storageFiles = storageFileMapper.getStorageFileByAttractionsId(String.valueOf(attractionsId));
        List<String> fileIdList = new ArrayList<>();
        for (StorageFile storageFile : storageFiles){
            LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Picture::getFileId, storageFile.getFileId());
            fileIdList.add(pictureMapper.selectOne(queryWrapper).getPictureId());
        }

        attractionsDetail.setFileIdList(fileIdList);

        Page<Essay> page = PageHelper.startPage(1, 10);
        PageInfo<Essay> pageInfo = new PageInfo<>(essayMapper.getEssayByAttractionsId(attractionsId));

        attractionsDetailVo.setAttractionsDetail(attractionsDetail);
        attractionsDetailVo.setEssay(pageInfo);
        return attractionsDetailVo;
    }

    @Override
    public List<AttractionsCityNameAndUrl> getCityNameAndUrl() {
        return attractionsMapper.getCityNameAndUrl();
    }
}

package org.hnxxxy.rg1b.service.essay.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.common.utils.StringUtils;
import org.hnxxxy.rg1b.domain.Attractions;
import org.hnxxxy.rg1b.domain.Essay;
import org.hnxxxy.rg1b.domain.StorageFile;
import org.hnxxxy.rg1b.domain.vo.EssayVo;
import org.hnxxxy.rg1b.mapper.AttractionsMapper;
import org.hnxxxy.rg1b.mapper.EssayMapper;
import org.hnxxxy.rg1b.service.essay.EssayService;
import org.hnxxxy.rg1b.service.file.IFileService;
import org.hnxxxy.rg1b.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EssayServiceImpl implements EssayService {

    @Resource
    private EssayMapper essayMapper;
    @Autowired
    private IFileService fileService;
    @Autowired
    private AttractionsMapper attractionsMapper;

    @Override
    public PageInfo<Essay> getEssayByPage(int attractionsId, int pageNo, int pageSize) {
        Page<Object> page = PageHelper.startPage(pageNo, pageSize);
        PageInfo<Essay> pageInfo = new PageInfo<>(essayMapper.getEssayByAttractionsId(attractionsId));
        return pageInfo;
    }

    @Override
    public List<Essay> selectRecommendEssays(int num) {
        List<Essay> essays = essayMapper.selectRecommendEssay(num);
        return essays;
    }

    @Override
    public EssayVo selectEssayDetail(String essayId) {
        LambdaQueryWrapper<Essay> essayQueryWrapper = new LambdaQueryWrapper<>();
        essayQueryWrapper
                .eq(Essay::getEssayId, essayId)
                .eq(Essay::getIsEffective, 1)
                .eq(Essay::getIsPublished, 1);
        Essay essay = essayMapper.selectOne(essayQueryWrapper);

        EssayVo essayVo = new EssayVo();
        BeanUtils.copyProperties(essay, essayVo);

        try {
            essayVo.setContent(fileService.getStorageFile(fileService.selectFileInfoById(essay.getArticleId()).getContent()));
        } catch (FileNotFoundException e) {
            return null;
        }
        return essayVo;
    }

    @Override
    public EssayVo insertEssay() {

        Essay essay = new Essay();
        essay.setEssayId(UUID.randomUUID().toString());
        essay.setDate(new Date());
        essay.setLookNum(0);
        essay.setUserId(SecurityUtils.getUserId());
        essay.setIsEffective(1);
        essay.setIsPublished(0);
        essay.setEssayTitle("");
        essay.setSummary("");
        essay.setCoverPictureId("");
        essay.setPlaceId("");
        essay.setPlaceType(2);

        StorageFile storageFile = null;
        try {
            storageFile = fileService.insertStorageFile("".getBytes(), "/essay/article/" + essay.getUserId(), essay.getEssayId() + ".txt");
        } catch (Exception e) {
            return null;
        }

        essay.setArticleId(storageFile.getFileId());

        essayMapper.insert(essay);

        EssayVo essayVo = new EssayVo();
        BeanUtils.copyProperties(essay, essayVo);
        essayVo.setContent("");

        return essayVo;
    }

    @Override
    public EssayVo updateEssay(EssayVo essayVo) {

        Essay essay = new Essay();
        BeanUtils.copyProperties(essayVo, essay);

        LambdaQueryWrapper<Essay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Essay::getEssayId, essay.getEssayId())
                .eq(Essay::getUserId, SecurityUtils.getUserId());

        Essay storeEssay = essayMapper.selectOne(queryWrapper);
        updateStoreEssayContent(storeEssay, essay);

        int isUpdated = essayMapper.update(essay, queryWrapper);

        if (isUpdated == 0) {
            return null;
        }

        BeanUtils.copyProperties(storeEssay, essayVo);

        try {
            fileService.updateFileContentById(essayVo.getArticleId(), essayVo.getContent().getBytes(), true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return essayVo;
    }

    /**
     * 添加运行添加的属性到库里的essay
     * @param storeEssay 库里的数据
     * @param newEssay 要添加的数据
     */
    private void updateStoreEssayContent(Essay storeEssay, Essay newEssay) {
        storeEssay.setEssayTitle(!StringUtils.isEmpty(newEssay.getEssayTitle()) ? newEssay.getEssayTitle() : "");
        storeEssay.setCoverPictureId(!StringUtils.isEmpty(newEssay.getCoverPictureId()) ? newEssay.getCoverPictureId() : "");
        storeEssay.setSummary(!StringUtils.isEmpty(newEssay.getSummary()) ? newEssay.getSummary() : "");
        storeEssay.setPlaceId(!StringUtils.isEmpty(newEssay.getPlaceId()) ? newEssay.getPlaceId() : "");
        if (ObjectUtils.isEmpty(newEssay.getPlaceType())) {
            newEssay.setPlaceType(2);
        }
        newEssay.setPlaceType(newEssay.getPlaceType() == 2 || newEssay.getPlaceType() == 0 ? newEssay.getPlaceType() : 2);
        storeEssay.setPlaceType(newEssay.getPlaceType());
        storeEssay.setDate(!ObjectUtils.isEmpty(newEssay.getDate()) ? newEssay.getDate() : new Date());
    }

    @Override
    public int publishEssay(String essayId) {
        LambdaQueryWrapper<Essay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Essay::getEssayId, essayId)
                .eq(Essay::getUserId, SecurityUtils.getUserId());
        Essay essay = essayMapper.selectOne(queryWrapper);

        if (ObjectUtils.isEmpty(essay) || StringUtils.isEmpty(essay.getEssayId())) {
            return 0;
        }
        essay.setIsPublished(1);
        int isUpdated = essayMapper.update(essay, queryWrapper);

        return isUpdated;
    }

    @Override
    public int deleteEssay(String essayId) {
        LambdaQueryWrapper<Essay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Essay::getEssayId, essayId)
                .eq(Essay::getUserId, SecurityUtils.getUserId());

        int isDeleted = essayMapper.delete(queryWrapper);

        return isDeleted;
    }

    @Override
    public List<Attractions> selectAttractionsList(String search, int nums) {
        List<Attractions> attractionsList = null;
        LambdaQueryWrapper<Attractions> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .orderBy(true, false, Attractions::getHeat)
                .orderBy(true, false, Attractions::getAppraiseScore);
        if (!StringUtils.isEmpty(search)) {
            queryWrapper.like(Attractions::getAttractionsName, "%" + search + "%");
        }
        queryWrapper.last("limit " + nums);
        attractionsList = attractionsMapper.selectList(queryWrapper);

        return attractionsList;
    }

    @Override
    public List<Essay> selectUserEssays(Long userId) {
        LambdaQueryWrapper<Essay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Essay::getUserId, userId).eq(Essay::getIsEffective, 1).eq(Essay::getIsPublished, 1);

        List<Essay> essays = essayMapper.selectList(queryWrapper);
        return essays;
    }
}

package org.hnxxxy.rg1b.service.essay;

import com.github.pagehelper.PageInfo;
import org.hnxxxy.rg1b.domain.Attractions;
import org.hnxxxy.rg1b.domain.Essay;
import org.hnxxxy.rg1b.domain.vo.EssayVo;

import java.util.List;

public interface EssayService {
    PageInfo<Essay> getEssayByPage(int attractionsId, int pageNo, int pageSize);

    /**
     * 获取首页的推荐游记
     * @param num
     * @return
     */
    List<Essay> selectRecommendEssays(int num);

    /**
     * 根据游记id获取游记详情
     * @param essayId
     * @return
     */
    EssayVo selectEssayDetail(String essayId);

    /**
     * 添加一条游记的信息(包括一个空的内容)
     * @return
     */
    EssayVo insertEssay();

    /**
     * 更新游记的信息(包括内容)
     * @param essayVo
     * @return
     */
    EssayVo updateEssay(EssayVo essayVo);

    /**
     * 发布一条游记
     * @param essayId
     * @return
     */
    int publishEssay(String essayId);

    /**
     * 删除一条游记
     * @param essayId
     * @return
     */
    int deleteEssay(String essayId);

    /**
     * 根据条件查询景点信息
     * @param search
     * @param nums
     * @return
     */
    List<Attractions> selectAttractionsList(String search, int nums);

    /**
     * 查看用户所写的所有有效游记
     * @param userId
     * @return
     */
    List<Essay> selectUserEssays(Long userId);
}

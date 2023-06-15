package org.hnxxxy.rg1b.controller.attractions.essay;

import com.github.pagehelper.PageInfo;
import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.domain.Attractions;
import org.hnxxxy.rg1b.domain.Essay;
import org.hnxxxy.rg1b.domain.Picture;
import org.hnxxxy.rg1b.domain.vo.EssayVo;
import org.hnxxxy.rg1b.service.essay.EssayService;
import org.hnxxxy.rg1b.service.file.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/attractions/essay")
public class EssayController {
    @Resource
    private EssayService essayService;
    @Autowired
    private IPictureService pictureService;

    /**
     * 分页获取游记信息
     * @param attractionsId
     * @return
     */
    @GetMapping("/detail/{attractionsId}")
    public PageInfo<Essay> getEssayByPage(@PathVariable Integer attractionsId, @RequestParam Integer pageNo, @RequestParam Integer pageSize){
        return essayService.getEssayByPage(attractionsId,pageNo, pageSize);
    }

    /**
     * 获取首页推送的游记
     * @param num
     * @return
     */
    @GetMapping("/recommend")
    public ResultVo getRecommendEssays(@RequestParam Integer num) {
        List<Essay> essays = essayService.selectRecommendEssays(num);
        return ResultVo.success(essays);
    }

    /**
     * 根据游记id获取游记内容详情
     * @param essayId
     * @return
     */
    @GetMapping("/detail/essay/{essayId}")
    public ResultVo getEssayDetail(@PathVariable String essayId) {
        EssayVo essayVo = essayService.selectEssayDetail(essayId);
        return ResultVo.success(essayVo);
    }

    /**
     * 添加一篇游记(不含内容)
     * @return
     */
    @PostMapping("/addEssay")
    public ResultVo addEssay() {
        EssayVo essay = essayService.insertEssay();
        return ResultVo.success(essay);
    }

    /**
     * 上传游记模块的图片
     * @param file
     * @return
     */
    @PostMapping("/picture")
    public ResultVo addPicture(@RequestParam MultipartFile file) {
        String userId = SecurityUtils.getUserId() + "";
        Picture picture = pictureService.insertPicture(file, "-1", 4, false, "/essay/picture/" + userId);
        return ResultVo.success(picture);
    }

    @GetMapping("/getAttractions")
    public ResultVo getAttractionsList(String search, int nums) {
        List<Attractions> attractions = essayService.selectAttractionsList(search, nums);
        return ResultVo.success(attractions);
    }

    /**
     * 修改一篇游记内容
     * @param essayVo
     * @return
     */
    @PutMapping("/updateEssayContent")
    public ResultVo updateEssay(@RequestBody EssayVo essayVo) {
        essayVo = essayService.updateEssay(essayVo);
        return ResultVo.success(essayVo);
    }

    /**
     * 发布一篇游记
     * @param essayId
     * @return
     */
    @PutMapping("/publishEssay/{essayId}")
    public ResultVo publishEssay(@PathVariable String essayId) {
        if (essayService.publishEssay(essayId) != 0) {
            return ResultVo.success("发布成功");
        }
        return ResultVo.error("发布失败");
    }

    /**
     * 删除某篇游记
     * @param essayId
     * @return
     */
    @DeleteMapping("/deleteEssay/{essayId}")
    public ResultVo deleteEssay(@PathVariable String essayId) {
        int isDeleted = essayService.deleteEssay(essayId);
        if (isDeleted != 0) {
            return ResultVo.success("删除成功");
        }
        return ResultVo.error("删除失败");
    }

    /**
     * 获取某用户写的所有游记
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResultVo getEssayByUser(@PathVariable Long userId) {
        List<Essay> essays = essayService.selectUserEssays(userId);
        return ResultVo.success(essays);
    }
}

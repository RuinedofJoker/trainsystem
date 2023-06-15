package org.hnxxxy.rg1b.controller.comment;

import org.hnxxxy.rg1b.common.PageVo;
import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.domain.CommentArea;
import org.hnxxxy.rg1b.domain.Picture;
import org.hnxxxy.rg1b.domain.vo.CommentVo;
import org.hnxxxy.rg1b.service.comment.ICommentAreaService;
import org.hnxxxy.rg1b.service.file.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentAreaService commentAreaService;
    @Autowired
    private IPictureService pictureService;

    /**
     * 分页查询某个文章下的主评论
     * @param articleId
     * @param commentType
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/list/{commentType}/{articleId}")
    public ResultVo getMainCommentList(@PathVariable("articleId") String articleId, @PathVariable("commentType") Integer commentType, Integer pageNo, Integer pageSize){
        //根据文章的id和type去CommentArea表里分页查询评论
        PageVo<CommentVo> commentAreas = commentAreaService.selectMainCommentPage(articleId, commentType, pageNo, pageSize);
        return ResultVo.success(commentAreas);
    }

    /**
     * 添加主要评论
     * @param comment
     * @return
     */
    @PostMapping("/addMainComment")
    public ResultVo addMainComment(@RequestBody CommentVo comment) {
        CommentVo insertedComment = commentAreaService.insertMainComment(comment.getContent(), comment.getArticleId(), comment.getCommentType());
        return ResultVo.success(insertedComment);
    }

    /**
     * 添加评论区图片
     * @param file
     * 0@return
     */
    @PostMapping("/picture")
    public ResultVo addPicture(@RequestParam MultipartFile file) {
        String userId = SecurityUtils.getUserId() + "";
        Picture picture = pictureService.insertPicture(file, "-1", 4, false, "/comment/picture/" + userId);
        return ResultVo.success(picture);
    }
}

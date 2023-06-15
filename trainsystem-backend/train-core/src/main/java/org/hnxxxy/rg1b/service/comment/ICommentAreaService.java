package org.hnxxxy.rg1b.service.comment;

import org.hnxxxy.rg1b.common.PageVo;
import org.hnxxxy.rg1b.domain.CommentArea;
import org.hnxxxy.rg1b.domain.vo.CommentVo;

import java.util.List;

public interface ICommentAreaService {

    /**
     * 添加一条评论
     */
    CommentVo insertMainComment(String commentContext, String articleId, Integer articleType);

    /**
     * 根据文章id分页查询评论
     */
    PageVo<CommentVo> selectMainCommentPage(String articleId, Integer articleType, Integer pageNo, Integer pageSize);
}

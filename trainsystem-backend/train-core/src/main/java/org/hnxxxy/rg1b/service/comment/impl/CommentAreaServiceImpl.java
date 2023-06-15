package org.hnxxxy.rg1b.service.comment.impl;

import org.hnxxxy.rg1b.common.PageVo;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.domain.CommentArea;
import org.hnxxxy.rg1b.domain.vo.CommentVo;
import org.hnxxxy.rg1b.mapper.CommentAreaMapper;
import org.hnxxxy.rg1b.service.comment.ICommentAreaService;
import org.hnxxxy.rg1b.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CommentAreaServiceImpl implements ICommentAreaService {

    @Autowired
    private CommentAreaMapper commentAreaMapper;

    @Override
    public CommentVo insertMainComment(String commentContext, String articleId, Integer articleType) {
        CommentArea comment = new CommentArea();
        comment.setCommentId(UUID.randomUUID().toString());
        comment.setArticleId(articleId);
        comment.setCommentType(articleType);
        comment.setDate(new Date());
        comment.setIsMainReply(1);
        comment.setLikeNum(0);
        comment.setIsEffective(1);
        comment.setMainReplyId("-1");
        comment.setReplyId("-1");
        comment.setUserId(SecurityUtils.getUserId());
        comment.setContent(commentContext.getBytes());

        int isInserted = commentAreaMapper.insert(comment);
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setContent(new String(comment.getContent()));

        if (isInserted != 0) {
            return commentVo;
        }
        return null;
    }

    @Override
    public PageVo<CommentVo> selectMainCommentPage(String articleId, Integer commentType, Integer pageNo, Integer pageSize) {
        List<CommentArea> commentAreas = commentAreaMapper.selectMainCommentPage(articleId, commentType, pageNo, pageSize);
        int totalNum = commentAreaMapper.selectMainCommentPageCount(articleId, commentType);
        List<CommentVo> commentVoList = new ArrayList<>();

        commentAreas.stream().forEach((comment -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment, commentVo);
            commentVo.setContent(new String(comment.getContent()));
            commentVoList.add(commentVo);
        }));

        PageVo<CommentVo> pageVo = new PageVo<>(totalNum, pageNo, pageSize, commentVoList);

        return pageVo;
    }


}

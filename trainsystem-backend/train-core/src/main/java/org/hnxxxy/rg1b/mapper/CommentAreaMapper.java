package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.CommentArea;

import java.util.List;

@Mapper
public interface CommentAreaMapper extends BaseMapper<CommentArea> {
    List<CommentArea> selectMainCommentPage(@Param("articleId") String articleId, @Param("commentType") Integer commentType, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    int selectMainCommentPageCount(@Param("articleId") String articleId, @Param("commentType") Integer commentType);
}

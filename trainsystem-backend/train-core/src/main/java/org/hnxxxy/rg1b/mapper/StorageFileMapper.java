package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.StorageFile;

import java.util.List;

@Mapper
public interface StorageFileMapper extends BaseMapper<StorageFile> {
    List<StorageFile> getStorageFileByAttractionsId(@Param("attractionsId")String attractionsId);
    StorageFile getStorageFileByFileId(@Param("fileId")String fileId);
    String getFileNameByFileId(@Param("fileId")String fileId);
    String getThumbnailFileNameByAttractionsId(@Param("attractionsId")String attractionsId);
}

package org.hnxxxy.rg1b.service.file;

import org.hnxxxy.rg1b.domain.Picture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.FileNotFoundException;

public interface IPictureService {

    /**
     * 根据图片id获取图片内容(直接以流的形式返回)
     * @param pictureId
     * @param outputStream
     */
    void getPictureById(String pictureId, ServletOutputStream outputStream) throws FileNotFoundException;

    /**
     * 添加图片
     * @param file 图片切片
     * @param ownedId 所属文章id
     * @param pictureType 图片类型
     * @param isCover 是否是封面
     * @param prefix 图片文件前缀
     * @return
     */
    Picture insertPicture(MultipartFile file, String ownedId, int pictureType, boolean isCover, String prefix);
}

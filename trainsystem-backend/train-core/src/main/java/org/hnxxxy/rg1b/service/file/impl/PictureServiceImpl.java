package org.hnxxxy.rg1b.service.file.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.hnxxxy.rg1b.common.utils.uuid.UUID;
import org.hnxxxy.rg1b.domain.Picture;
import org.hnxxxy.rg1b.domain.StorageFile;
import org.hnxxxy.rg1b.mapper.PictureMapper;
import org.hnxxxy.rg1b.mapper.StorageFileMapper;
import org.hnxxxy.rg1b.service.file.IFileService;
import org.hnxxxy.rg1b.service.file.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class PictureServiceImpl implements IPictureService {

    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private IFileService fileService;

    @Override
    public void getPictureById(String pictureId, ServletOutputStream outputStream) throws FileNotFoundException {
        LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Picture::getPictureId, pictureId);
        Picture picture = pictureMapper.selectOne(queryWrapper);

        try {
            fileService.getStorageFile(fileService.selectFileInfoById(picture.getFileId()).getContent(), outputStream, true);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }

    @Override
    public Picture insertPicture(MultipartFile file, String ownedId, int pictureType, boolean isCover, String prefix) {
        Picture picture = new Picture();
        picture.setPictureId(UUID.randomUUID().toString());
        picture.setPictureType(pictureType);
        picture.setOwnedId(ownedId);
        picture.setIsCover(isCover ? 1 : 0);
        try {
            StorageFile storageFile = fileService.insertStorageFile(file, prefix);
            picture.setFileId(storageFile.getFileId());
            pictureMapper.insert(picture);
        } catch (IOException e) {
            return null;
        }

        return picture;
    }
}

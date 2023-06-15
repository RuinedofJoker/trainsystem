package org.hnxxxy.rg1b.service.file.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.io.FilenameUtils;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.common.utils.StringUtils;
import org.hnxxxy.rg1b.common.utils.file.FileUploadUtils;
import org.hnxxxy.rg1b.common.utils.file.MimeTypeUtils;
import org.hnxxxy.rg1b.domain.StorageFile;
import org.hnxxxy.rg1b.mapper.StorageFileMapper;
import org.hnxxxy.rg1b.service.file.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class FileServiceImpl implements IFileService {

    @Autowired
    private StorageFileMapper storageFileMapper;

    @Value("${train.profile}")
    private String profile;

    @Override
    public StorageFile insertStorageFile(MultipartFile file, String prefix) throws IOException {

        String filename = FileUploadUtils.upload(profile + prefix, file);
        if ("/profile".equals(filename.substring(0, 8))) {
            filename = filename.substring(8);
        }
        StorageFile storageFile = new StorageFile();
        storageFile.setFileId(UUID.randomUUID().toString());
        storageFile.setUserId(SecurityUtils.getUserId());
        storageFile.setContent(filename);
        //获取文件后缀名
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        storageFile.setContentType(extension);
        storageFile.setDate(new Date());
        String[] filePathArray = filename.split("/");
        filename = filePathArray[filePathArray.length - 1];
        storageFile.setFilename(filename);

        int insert = storageFileMapper.insert(storageFile);

        if (insert != 0){
            return storageFile;
        }else {
            throw new IOException("入库错误");
        }
    }

    @Override
    public StorageFile insertStorageFile(byte[] fileContent, String prefix, String fileName) throws Exception {

        String filePath = prefix + "/" + fileName;
        createFile(profile + filePath);
        StorageFile storageFile = new StorageFile();
        storageFile.setFileId(UUID.randomUUID().toString());
        storageFile.setFilename(fileName);
        storageFile.setDate(new Date());
        storageFile.setUserId(SecurityUtils.getUserId());
        storageFile.setContent(filePath);
        String[] fileType = fileName.split("\\.");
        storageFile.setContentType(fileType.length == 1 ? "" : fileType[fileType.length - 1]);
        addFileContent(profile + storageFile.getContent(), fileContent, true);
        storageFileMapper.insert(storageFile);

        return storageFile;
    }

    /**
     * 创建不存在的文件(带目录,如果该文件存在则不创建)
     * @param path
     * @throws IOException
     */
    private static void createFile(String path) throws IOException {
        String[] pathArray = path.split("/");
        String onlyPath = path.substring(0, path.length() - pathArray[pathArray.length - 1].length() - 1);

        File fileDir = new File(onlyPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    public StorageFile selectFileInfoById(String fileId) {
        LambdaQueryWrapper<StorageFile> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StorageFile::getFileId, fileId);
        StorageFile storageFile = storageFileMapper.selectOne(queryWrapper);
        return storageFile;
    }

    @Override
    public void getStorageFile(String content, OutputStream outputStream, boolean close) throws FileNotFoundException {
        try {
            FileInputStream inputStream = new FileInputStream(profile + content);
            try {
                int len;
                byte[] bytes = new byte[1024];

                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes,0,len);
                    outputStream.flush();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (close) {
                    outputStream.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            }
        }catch (Exception e) {
            throw new FileNotFoundException("文件未找到");
        }
    }

    @Override
    public String getStorageFile(String content) throws FileNotFoundException {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            FileInputStream inputStream = new FileInputStream(profile + content);
            try {
                int len;
                byte[] bytes = new byte[1024];

                while ((len = inputStream.read(bytes)) != -1){
                    stringBuffer.append(new String(bytes, 0, len));
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (inputStream != null){
                    inputStream.close();
                }
            }
        }catch (Exception e) {
            throw new FileNotFoundException("文件未找到");
        }
        return stringBuffer.toString();
    }

    @Override
    public StorageFile updateFileContentById(String fileId, byte[] content, boolean overwrite) throws Exception {
        StorageFile storageFile = selectFileInfoById(fileId);
        addFileContent(profile + storageFile.getContent(), content, overwrite);
        return storageFile;
    }

    /**
     * 向一个已经存在的文件里添加内容
     * @param path 文件路径(绝对路径)
     * @param content 添加的内容
     * @param overwrite 是否以覆盖的形式添加
     * @throws Exception
     */
    private void addFileContent(String path, byte[] content, boolean overwrite) throws Exception {
        File file = new File(path);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, !overwrite);
            if (!overwrite) {
                outputStream.write("\n".getBytes());
            }
            outputStream.write(content);
            outputStream.flush();
        } catch (Exception e) {
            throw new Exception(e);
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

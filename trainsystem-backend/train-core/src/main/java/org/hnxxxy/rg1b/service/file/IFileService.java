package org.hnxxxy.rg1b.service.file;

import org.hnxxxy.rg1b.domain.StorageFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public interface IFileService {

    /**
     * 将文件入库并保存到本地
     * @param file 文件切片
     * @param prefix 文件路径前缀
     * @return
     * @throws IOException
     */
    StorageFile insertStorageFile(MultipartFile file, String prefix) throws IOException;

    /**
     * 将文件入库并保存到本地
     * @param fileContent 文件内容
     * @param prefix 文件路径前缀
     * @param fileName 文件名
     * @return
     * @throws Exception
     */
    StorageFile insertStorageFile(byte[] fileContent, String prefix, String fileName) throws Exception;

    /**
     * 根据文件id找到文件信息
     * @param fileId
     * @return
     */
    StorageFile selectFileInfoById(String fileId);

    /**
     * 获取文件(直接向前端返回流)
     * @param content 文件路径(StorageFile库里的content字段)
     * @param outputStream 响应获取的输出流
     * @param close 是否关闭输出流
     * @throws FileNotFoundException
     */
    void getStorageFile(String content, OutputStream outputStream, boolean close) throws FileNotFoundException;

    /**
     * 读取文本文件内容返回成字符串
     * @param content 文件路径(StorageFile库里的content字段)
     * @return
     * @throws FileNotFoundException
     */
    String getStorageFile(String content) throws FileNotFoundException;

    /**
     * 根据文件id更新文件内容
     * @param fileId
     * @param content 新增的内容
     * @param overwrite 是否以覆盖的方式新增
     * @return
     * @throws Exception
     */
    StorageFile updateFileContentById(String fileId, byte[] content, boolean overwrite) throws Exception;
}

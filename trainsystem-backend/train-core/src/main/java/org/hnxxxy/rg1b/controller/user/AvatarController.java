package org.hnxxxy.rg1b.controller.user;

import org.hnxxxy.rg1b.common.core.controller.BaseController;
import org.hnxxxy.rg1b.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class AvatarController extends BaseController {

    @Value("${train.profile}")
    private String profile;

    /**
     * 获取用户头像
     */
    @GetMapping("/profile/avatar/{yyyy}/{MM}/{dd}/{avatar}")
    public AjaxResult avatar(@PathVariable String yyyy, @PathVariable String MM, @PathVariable String dd, @PathVariable String avatar, ServletOutputStream outputStream) throws IOException {
        avatar = "/avatar"+"/"+yyyy+"/"+MM+"/"+dd+"/"+avatar;
        avatar = profile+avatar;
        try {
            FileInputStream inputStream = new FileInputStream(new File(avatar));
            try {
                int len;
                byte[] bytes = new byte[1024];

                while ((len = inputStream.read(bytes)) != -1){
                    outputStream.write(bytes,0,len);
                    outputStream.flush();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                outputStream.close();
                if (inputStream != null){
                    inputStream.close();
                }
            }
        }catch (FileNotFoundException e){
            return AjaxResult.error("文件未找到");
        }
        return AjaxResult.success("ok");
    }
}

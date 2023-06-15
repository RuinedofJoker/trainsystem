package org.hnxxxy.rg1b.controller.picture;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.service.file.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private IPictureService pictureService;

    @GetMapping("/{pictureId}")
    public ResultVo getPicture(@PathVariable("pictureId") String pictureId, ServletOutputStream outputStream) {
        try {
            pictureService.getPictureById(pictureId, outputStream);
        } catch (Exception e) {
            return ResultVo.error("文件未找到");
        }
        return ResultVo.success("ok");
    }
}

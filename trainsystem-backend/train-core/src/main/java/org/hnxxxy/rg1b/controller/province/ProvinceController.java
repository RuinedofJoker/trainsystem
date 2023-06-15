package org.hnxxxy.rg1b.controller.province;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/province")
public class ProvinceController {

    @Autowired
    private IProvinceService provinceService;

    /**
     * 获取所有的省份信息
     */
    @GetMapping()
    public ResultVo getAllProvince(){
        return ResultVo.success(provinceService.selectAllProvince());
    }

    /**
     * 根据省份的名字来获取该省份所有的城市
     * @Param provinceName 省份名字
     */
    @GetMapping("/city")
    public ResultVo getAllCityByProvinceName(@RequestParam String provinceName){
        return ResultVo.success(provinceService.selectAllCityByProvinceName(provinceName));
    }
}

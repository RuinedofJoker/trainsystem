package org.hnxxxy.rg1b.common;

import org.hnxxxy.rg1b.common.constant.HttpStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 */
@ApiModel(value = "ResultVo", description = "响应信息主体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo<D> implements Serializable {

    private static final Integer SUCCESS = HttpStatus.SUCCESS;
    private static final Integer ERROR = HttpStatus.ERROR;

    @ApiModelProperty("响应状态码")
    private Integer code;
    @ApiModelProperty("响应信息")
    private String msg;
    @ApiModelProperty("响应数据")
    private D data;

    public static <D> ResultVo<D> success(D data){
        ResultVo<D> result = new ResultVo();
        result.code = SUCCESS;
        result.data = data;
        return result;
    }

    public static <D> ResultVo<D> success(String msg){
        ResultVo<D> result = new ResultVo();
        result.code = SUCCESS;
        result.msg = msg;
        return result;
    }

    public static <D> ResultVo<D> error(String msg){
        ResultVo<D> result = new ResultVo();
        result.code = ERROR;
        result.msg = msg;
        return result;
    }
}

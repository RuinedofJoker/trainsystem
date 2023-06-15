package org.hnxxxy.rg1b.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<D> implements Serializable {
    private Integer totalNum;
    private Integer pageNo;
    private Integer pageSize;
    private Integer pages;
    private List<D> data;

    public PageVo(Integer totalNum, Integer pageNo, Integer pageSize, List<D> data) {
        this.totalNum = totalNum;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.data = data;

        this.pages = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
    }
}

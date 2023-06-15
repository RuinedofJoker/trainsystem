package org.hnxxxy.rg1b.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hnxxxy.rg1b.domain.dto.IntelCity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainIntelVo {
    //前端传输的基本信息
    private IntelCity fromCity;//从哪个城市来
    private IntelCity toCity;//到哪个城市去
    private List<IntelCity> throughCity;//途中一定要经过的城市

    public List<IntelCity> getThroughCity() {
        if (throughCity == null){
            return new ArrayList<>();
        }
        return throughCity;
    }
}

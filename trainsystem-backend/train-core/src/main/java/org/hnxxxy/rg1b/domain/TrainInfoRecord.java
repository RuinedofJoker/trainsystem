package org.hnxxxy.rg1b.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainInfoRecord {
    private Integer recordDate;
    private String recordTrainType;
    private Integer isEffective;
}

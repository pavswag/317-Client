package com.client.definitions;

import com.client.definitions.skeletal.ABW;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SeqBase {
    private int groupId;
    @EqualsAndHashCode.Include
    private int[] types;
    @EqualsAndHashCode.Include
    private int[][] groupLabels;
    private int length;
    public ABW ABW;
}

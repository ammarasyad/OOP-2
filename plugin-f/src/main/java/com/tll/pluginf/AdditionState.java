package com.tll.pluginf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionState {

    private BigDecimal priceAddition;
    private boolean enabled;

}

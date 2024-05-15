package com.client.graphics.interfaces;

import lombok.Data;

@Data
public class WidgetChildEvent {

    private final int interfaceId;
    private final int x;
    private final int y;
}

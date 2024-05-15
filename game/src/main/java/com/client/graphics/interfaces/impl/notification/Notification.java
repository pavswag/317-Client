package com.client.graphics.interfaces.impl.notification;

import lombok.Data;

@Data
public class Notification {
    private final String title;
    private final String topDescription;
    private final String bottomDescription;
    private final int item;
}

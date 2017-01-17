package com.daniel.hu.model;

import lombok.Data;

/**
 * Created by Daniel on 14/01/2017.
 */
@Data
public class SensorModel {
    private int temperature;
    private int humidity;
    private int pm25;
    private Long timestamp;
}

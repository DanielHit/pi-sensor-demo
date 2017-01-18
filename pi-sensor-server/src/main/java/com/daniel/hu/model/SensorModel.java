package com.daniel.hu.model;

import lombok.Data;

/**
 * Created by Daniel on 14/01/2017.
 */
@Data
public class SensorModel {
    private double temperature;
    private double humidity;
    private Long timestamp;
}

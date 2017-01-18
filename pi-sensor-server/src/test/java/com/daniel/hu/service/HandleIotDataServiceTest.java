package com.daniel.hu.service;

import com.daniel.hu.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Daniel on 17/01/2017.
 */
public class HandleIotDataServiceTest extends BaseTest {
    @Autowired
    private HandleIotDataService iotDataService;

    @Test
    public void insertSensorData() throws Exception {

    }

    @Test
    public void querySensorData() throws Exception {
        iotDataService.querySensorData(new Date().getTime());
    }

}
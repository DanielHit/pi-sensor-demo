package com.daniel.hu.web;

import com.daniel.hu.model.SensorModel;
import com.daniel.hu.service.HandleIotDataService;
import com.google.appengine.repackaged.com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Daniel on 17/01/2017.
 */
@RestController
@RequestMapping("/api")
public class SensorController {

    @Autowired
    private HandleIotDataService handleIotDataService;

    public static final Logger LOGGER = LoggerFactory.getLogger(SensorController.class);

    @RequestMapping(value = "/sensor", method = RequestMethod.POST)
    public String collect(@RequestBody SensorModel sensorModel) {
        handleIotDataService.insertSensorData(sensorModel);
        LOGGER.info("get data from iot {}", new Gson().toJson(sensorModel));
        return "success";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/sensor/data", method = RequestMethod.GET)
    public SensorModel getData(Long timestamp) {
        return handleIotDataService.querySensorData(timestamp);
    }

}

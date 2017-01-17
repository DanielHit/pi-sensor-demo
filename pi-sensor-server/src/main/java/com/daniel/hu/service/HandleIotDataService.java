package com.daniel.hu.service;

import com.daniel.hu.model.SensorModel;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniel on 14/01/2017.
 */
@Service
public class HandleIotDataService {

    public static final String TEMPERATURE = "temperature";
    public static final String HUMIDITY = "humidity";
    public static final String PM_25 = "pm25";

    private InfluxDB influxDB;

    private String dbName;

    @PostConstruct
    public void init() {
        dbName = "indoor_data";
        influxDB = InfluxDBFactory.connect("http://10.4.233.86:8086/");
    }

    public void insertSensorData(SensorModel sensorModel) {
        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy("autogen")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        Point point1 = Point.measurement("sensor")
                .time(new Date().getTime(), TimeUnit.MILLISECONDS)
                .addField(TEMPERATURE, sensorModel.getTemperature())
                .addField(HUMIDITY, sensorModel.getHumidity())
                .addField(PM_25, sensorModel.getPm25())
                .build();

        batchPoints.point(point1);
        influxDB.write(batchPoints);

        Query query = new Query("SELECT temperature FROM sensor", dbName);
        QueryResult result = influxDB.query(query);
        result.getResults().stream().forEach(p -> System.out.println(p.getSeries()));
    }

    // TODO: 17/01/2017 需要更加的细化
    public SensorModel querySensorData(Long timestamp) {
        String dbName = "indoor_data";
        Long minTime = timestamp - 60 * 1000;  // 最小为一分钟之前
        Long maxTime = timestamp + 60 * 1000;  // 最大为一分钟之后

        Query query = new Query("SELECT temperature FROM sensor where time > " + minTime + " and time < " + maxTime , dbName);
        QueryResult result = influxDB.query(query);
        result.getResults().stream().forEach(p -> System.out.println(p.getSeries()));
        return new SensorModel();
    }

}

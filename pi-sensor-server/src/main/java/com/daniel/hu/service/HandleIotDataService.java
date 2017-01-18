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
import java.util.List;
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
                .build();

        batchPoints.point(point1);
        influxDB.write(batchPoints);

        Query query = new Query("SELECT temperature FROM sensor", dbName);
        QueryResult result = influxDB.query(query);
        result.getResults().stream().forEach(p -> System.out.println(p.getSeries()));
    }

    public SensorModel querySensorData(Long timestamp) {
        SensorModel sensorModel = new SensorModel();
        String dbName = "indoor_data";

        Query query = new Query("SELECT humidity, temperature FROM indoor_data.autogen.sensor WHERE time > now() - 2m" , dbName);
        QueryResult result = influxDB.query(query);
        List<QueryResult.Result> results = result.getResults();

        final double[] temperature = {0};
        final double[]  humidity = {0};

        if (results.stream().findFirst().isPresent()) {
            results.stream().findFirst().get().getSeries().forEach(p-> {
                List<List<Object>> list = p.getValues();
                list.forEach(model -> {
                    temperature[0] += Double.parseDouble(model.get(2).toString());
                    humidity[0] += Double.parseDouble(model.get(1).toString());
                });
                temperature[0] /= list.size();
                humidity[0] /= list.size();
            });
        }

        sensorModel.setTemperature(temperature[0]);
        sensorModel.setHumidity(humidity[0]);
        sensorModel.setTimestamp(new Date().getTime());

        return sensorModel;
    }

}

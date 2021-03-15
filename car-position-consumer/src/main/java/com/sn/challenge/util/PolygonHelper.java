package com.sn.challenge.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sn.challenge.model.Position;
import com.sn.challenge.model.polygon.MyPolygon;
import com.sn.challenge.repository.PolygonRepository;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ehsan Sh
 */

@Component
@Slf4j
public class PolygonHelper {

    @Autowired
    private PolygonRepository polygonRepository;

    @Value("${polygons.url}")
    public String polygonUrl;

    private List<MyPolygon> myPolygonList;

    @PostConstruct
    private void loadPolygonsDataFromURI() {
        myPolygonList = polygonRepository.findAll();
        if (myPolygonList.size() == 0) {

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                URL url = new URL(polygonUrl);
                MyPolygon[] myPolygons = objectMapper.readValue(url, MyPolygon[].class);
                myPolygonList = Arrays.asList(myPolygons);
            } catch (IOException e) {
                log.warn("Cannot read polygons data " + e.getMessage());
                e.printStackTrace();
                myPolygonList = Collections.emptyList();
            }
        }
    }

    public List<MyPolygon> getPolygonList() {
        return myPolygonList;
    }

    public String findVehiclePolygon(Position position){

        GeometryFactory gf = new GeometryFactory();

        for (MyPolygon myPolygon : myPolygonList) {

            Coordinate[] coordinateArr = myPolygon.getGeometry()
                    .getCoordinates().get(0)
                    .stream()
                    .map(e -> new Coordinate(e.get(1), e.get(0)))
                    //.skip(1)
                    .toArray(Coordinate[]::new);


            LinearRing jtsRing = gf.createLinearRing(coordinateArr);
            Polygon poly = gf.createPolygon(jtsRing, null);
            //Polygon poly = gf.createPolygon(coordinateArr);

            Coordinate coordinate = new Coordinate(
                    position.getLatitude(),
                    position.getLongitude());

            Point pt = gf.createPoint(coordinate);
            if (poly.contains(pt)) {
                return  myPolygon.getId();
            }
        }
        return "";
    }
}

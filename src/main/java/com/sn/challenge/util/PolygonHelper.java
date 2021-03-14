package com.sn.challenge.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sn.challenge.model.polygon.MyPolygon;
import lombok.extern.slf4j.Slf4j;
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

    @Value("${polygons.url}")
    public String polygonUrl;

    private List<MyPolygon> myPolygonList;

    @PostConstruct
    private void loadPolygonsDataFromURI() {
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

    public List<MyPolygon> getPolygonList() {
        return myPolygonList;
    }
}

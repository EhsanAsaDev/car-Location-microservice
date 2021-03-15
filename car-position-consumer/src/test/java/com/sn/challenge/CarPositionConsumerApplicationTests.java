package com.sn.challenge;

import com.sn.challenge.model.Vehicle;
import com.sn.challenge.model.polygon.MyPolygon;
import com.sn.challenge.service.VehiclesService;
import com.sn.challenge.util.PolygonHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CarPositionConsumerApplicationTests {

    @Autowired
    private VehiclesService vehiclesService;

    @Autowired
    private PolygonHelper polygonHelper;


    @Test
    void contextLoads() {
    }

    @Test
    //Todo testFailed!
    public void metaDataAndLibraryTest() throws Exception {
        GeometryFactory gf = new GeometryFactory();

        List<MyPolygon> myPolygonList = polygonHelper.getPolygonList();
        Vehicle[] vehicleArray = vehiclesService.getVehicleInfo();
        boolean testFlag=false;

        for (Vehicle vehicle : vehicleArray) {
            boolean flag = false;
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
                        vehicle.getPosition().getLatitude(),
                        vehicle.getPosition().getLongitude());

                Point pt = gf.createPoint(coordinate);
                if (poly.contains(pt)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.out.println("*** vehicle  id: " + vehicle.getId() + " vehicle  vin: " + vehicle.getVin() + "  not in any polygon: "
                        + vehicle.getPosition().getLatitude() + " , " + vehicle.getPosition().getLongitude());
                testFlag =true;
            }

        }
        Assertions.assertFalse(testFlag);

    }


}

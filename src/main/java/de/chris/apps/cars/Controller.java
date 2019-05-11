package de.chris.apps.cars;

import de.chris.apps.cars.entitiy.Overview;
import de.chris.apps.cars.vehicle.JsonData;
import de.chris.apps.cars.vehicle.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class Controller {

    @RequestMapping(value = "/newVehicle", method = RequestMethod.POST)
    public ResponseEntity<Void> addVehicle(@RequestBody JsonData data) throws IOException {
        Vehicle.addVehicle(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/updateOdo", method = RequestMethod.POST)
    public ResponseEntity<Void> updateOdometer(@RequestParam String vin, @RequestParam int odo) throws IOException {
        Vehicle.getVehicle(vin).updateOdometer(odo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getDistanceDiff", method = RequestMethod.GET)
    public ResponseEntity<Float> getDistanceDiff(@RequestParam String vin) throws IOException {
        return new ResponseEntity<>(Vehicle.getVehicle(vin).getDistanceDifference(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getOverview", method = RequestMethod.GET)
    public ResponseEntity<Overview> getOverview(@RequestParam String vin) throws IOException {
        return new ResponseEntity<>(new Overview(Vehicle.getVehicle(vin)), HttpStatus.OK);
    }
}

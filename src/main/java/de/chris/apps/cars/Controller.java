package de.chris.apps.cars;

import de.chris.apps.cars.entitiy.Overview;
import de.chris.apps.cars.vehicle.History;
import de.chris.apps.cars.vehicle.JsonData;
import de.chris.apps.cars.vehicle.Vehicle;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class Controller {

    @ApiOperation(value = "Adding a new vehicle")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Vehicle added successfully"),
            @ApiResponse(code = 500, message = "Vehicle file not found or vehicle is already existing") })
    @RequestMapping(value = "/newVehicle", method = RequestMethod.POST)
    public ResponseEntity<Void> addVehicle(@RequestBody JsonData data) throws IOException {
        Vehicle.addVehicle(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Update the odometer of a vehicle in kilometers")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Odometer update was successful"),
            @ApiResponse(code = 500, message = "Vehicle does not exist or could not get file") })
    @RequestMapping(value = "/updateOdo", method = RequestMethod.POST)
    public ResponseEntity<Void> updateOdometer(@RequestParam String vin, @RequestParam int odo) throws IOException {
        Vehicle.getVehicle(vin).updateOdometer(odo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Get the distance difference in kilometers. When the number is positive these are "
            + "the kilometers you saved till today.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Float.class),
            @ApiResponse(code = 500, message = "Vehicle does not exist or could not get file") })
    @RequestMapping(value = "/getDistanceDiff", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Float> getDistanceDiff(@RequestParam String vin) throws IOException {
        return new ResponseEntity<>(Vehicle.getVehicle(vin).getDistanceDifference(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get an overview about your leasing data.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Overview.class),
            @ApiResponse(code = 500, message = "Vehicle does not exist or could not get file") })
    @RequestMapping(value = "/getOverview", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Overview> getOverview(@RequestParam String vin) throws IOException {
        return new ResponseEntity<>(new Overview(Vehicle.getVehicle(vin)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get an overview about your leasing data.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Vehicle does not exist or could not get file") })
    @RequestMapping(value = "/getHistory", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<History[]> getHistory(@RequestParam String vin) throws IOException {
        return new ResponseEntity<>(Vehicle.getVehicle(vin).getHistories(), HttpStatus.OK);
    }
}

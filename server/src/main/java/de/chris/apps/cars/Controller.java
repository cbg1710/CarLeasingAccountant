package de.chris.apps.cars;

import de.chris.apps.cars.entitiy.NewVehicle;
import de.chris.apps.cars.entitiy.Overview;
import de.chris.apps.cars.entitiy.VehicleEntity;
import de.chris.apps.cars.vehicle.History;
import de.chris.apps.cars.entitiy.Trend;
import de.chris.apps.cars.vehicle.Vehicle;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class Controller {

        @ApiOperation(value = "Adding a new vehicle")
        @ApiResponses(value = {@ApiResponse(code = 200, message = "Vehicle added successfully"),
                        @ApiResponse(code = 500,
                                        message = "Vehicle file not found or vehicle is already existing")})
        @PostMapping(value = "/newVehicle")
        public ResponseEntity<Void> addVehicle(@RequestBody NewVehicle data) throws IOException {
                Vehicle.addVehicle(data);
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @ApiOperation(value = "Update the odometer of a vehicle in kilometers")
        @ApiResponses(value = {@ApiResponse(code = 200, message = "Odometer update was successful"),
                        @ApiResponse(code = 500,
                                        message = "Vehicle does not exist or could not get file")})
        @PostMapping(value = "/updateOdo")
        public ResponseEntity<Void> updateOdometer(@RequestParam String vin, @RequestParam int odo)
                        throws IOException {
                Vehicle.getVehicle(vin).updateOdometer(odo);
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @ApiOperation(value = "Get the distance difference in kilometers. When the number is positive these are "
                        + "the kilometers you saved till today.")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "Success", response = Float.class),
                        @ApiResponse(code = 500,
                                        message = "Vehicle does not exist or could not get file")})
        @GetMapping(value = "/getDistanceDiff", produces = "application/json")
        public ResponseEntity<Integer> getDistanceDiff(@RequestParam String vin) throws IOException {
                return new ResponseEntity<>(Vehicle.getVehicle(vin).getDistanceDifference(),
                                HttpStatus.OK);
        }

        @ApiOperation(value = "Calculates how much kilometers are left for a long road trip. " 
                + "If the value is negative no such trip should be made.")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "Success", response = Integer.class),
                        @ApiResponse(code = 500,
                                        message = "Vehicle does not exist or could not get file")})
        @GetMapping(value = "/holiday", produces = "application/json")
        public ResponseEntity<Integer> holiday(@RequestParam String vin) throws IOException {
                return new ResponseEntity<>(Vehicle.getVehicle(vin).holiday(),
                                HttpStatus.OK);
        }

        @ApiOperation(value = "Average kilometer per day trend. RISING if driven kilometer at the last day was higher than the days bevore. It the " 
                + "kolimeters are less the result is FALLING. If the kilometers are the same the response is STABLE. If no historsy data is present the result is NO TREND")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "Success", response = Trend.class),
                        @ApiResponse(code = 500,
                                        message = "Vehicle does not exist or could not get file")})
        @GetMapping(value = "/trend", produces = "application/json")
        public ResponseEntity<Trend> trend(@RequestParam String vin) throws IOException {
                return new ResponseEntity<>(Vehicle.getVehicle(vin).calculateTrend(),
                                HttpStatus.OK);
        }

        @ApiOperation(value = "Get an overview about your leasing data.")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "Success", response = Overview.class),
                        @ApiResponse(code = 500,
                                        message = "Vehicle does not exist or could not get file")})
        @GetMapping(value = "/getOverview", produces = "application/json")
        public ResponseEntity<Overview> getOverview(@RequestParam String vin) throws IOException {
                return new ResponseEntity<>(new Overview(Vehicle.getVehicle(vin)), HttpStatus.OK);
        }

        @ApiOperation(value = "Get an overview about your leasing data.")
        @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(
                        code = 500, message = "Vehicle does not exist or could not get file")})
        @GetMapping(value = "/getHistory", produces = "application/json")
        public ResponseEntity<History[]> getHistory(@RequestParam String vin,
                        @RequestParam(name = "dataPoints", required = false) Integer dataPoints)
                        throws IOException {

                History[] histories = Vehicle.getVehicle(vin).getHistories();

                if (dataPoints == null || dataPoints <= 0 || dataPoints >= histories.length) {
                        return new ResponseEntity<>(histories, HttpStatus.OK);
                }

                History[] result = Arrays.copyOfRange(histories, histories.length - dataPoints,
                                histories.length);
                return new ResponseEntity<>(result, HttpStatus.OK);
        }

        @ApiOperation(value = "Get all vehicles.")
        @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(
                        code = 500, message = "Vehicle does not exist or could not get file")})
        @GetMapping(value = "/listVehicles", produces = "application/json")
        public ResponseEntity<List<VehicleEntity>> listVehicles() {
                return new ResponseEntity<>(Vehicle.listVehicles(),
                                HttpStatus.OK);
        }
}

package util

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"

	"strings"

	entitiy "github.com/cbg1710/CarAccountant/entity"
)

const fileExtension = ".JSON"
const dirPath = "/Users/christianglasel/Work/Repositories/"

func visit(files *[]string) filepath.WalkFunc {
	return func(path string, f os.FileInfo, err error) error {
		if err != nil {
			return err
		}

		if filepath.Ext(f.Name()) == fileExtension {
			//veh := strings.Split(f.Name(), ".")
			*files = append(*files, path)
		}
		return nil
	}
}

// ListVehicleFiles returns a list of all vehicle json files.
func listVehicleFiles() []string {
	var files []string
	filepath.Walk(dirPath, visit(&files))
	return files
}

func getVehicle(vin string) string {
	for _, file := range listVehicleFiles() {
		if strings.Contains(file, vin) {
			return file
		}
	}
	return ""
}

// GetVehicleOverview returns a vehicle overview for the given vin
func GetVehicleOverview(vin string) entitiy.VehicleOverview {
	fmt.Println("Overview...")
	vehicle := getVehicle(vin)
	var result entitiy.VehicleOverview
	fVal, _ := ioutil.ReadFile(vehicle)
	err := json.Unmarshal(fVal, &result)
	if err != nil {
		log.Fatal(err)
	}
	return result
}

// GetVehicles returns a list of all vehicles.
func GetVehicles() []entitiy.Vehicle {
	fmt.Println("get vehicles")
	var result []entitiy.Vehicle
	vehicleFiles := listVehicleFiles()
	for _, file := range vehicleFiles {
		fVal, _ := ioutil.ReadFile(file)

		var vehicleOverview entitiy.VehicleOverview
		json.Unmarshal(fVal, &vehicleOverview)

		var vehicle entitiy.Vehicle
		vehicle.Name = vehicleOverview.Data.Name
		vehicle.Vin = vehicleOverview.Vin
		result = append(result, vehicle)
	}

	return result
}
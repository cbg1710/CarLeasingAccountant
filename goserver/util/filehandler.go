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

// GetVehicle returns all information about the given vehicle
func GetVehicle(vin string) entitiy.VehicleJSONFile {
	vehicle := getVehicle(vin)
	var result entitiy.VehicleJSONFile
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

		var vehicleFile entitiy.VehicleJSONFile
		json.Unmarshal(fVal, &vehicleFile)

		var vehicle entitiy.Vehicle
		vehicle.Name = vehicleFile.Data.Name
		vehicle.Vin = vehicleFile.Vin
		result = append(result, vehicle)
	}

	return result
}

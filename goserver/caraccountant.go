package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"

	entity "github.com/cbg1710/CarAccountant/entity"
)

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hi there, I love %s!", r.URL.Path[1:])
}

func listVehiclesHandler(w http.ResponseWriter, r *http.Request) {
	vehicles := entity.GetVehicles()
	json, _ := json.Marshal(vehicles)
	w.Write(json)
}

func getOverviewHandler(w http.ResponseWriter, r *http.Request) {
	key, ok := r.URL.Query()["vin"]

	fmt.Println(key)
	if !ok || len(key) != 1 {
		log.Println("Url Param 'vin' is missing")
		w.WriteHeader(http.StatusNotFound)
		return
	}

	vin := key[0]
	overViewBuilder := entity.VehicleOverviewBuilder{
		VIN:      vin,
		JSONFile: entity.GetVehicle(vin),
	}
	json, _ := json.Marshal(overViewBuilder.GetOverview())
	w.Write(json)
}

func main() {
	http.HandleFunc("/", handler)
	http.HandleFunc("/listVehicles", listVehiclesHandler)
	http.HandleFunc("/getOverview", getOverviewHandler)
	log.Fatal(http.ListenAndServe(":8080", nil))
	fmt.Println("Starting the game")
}

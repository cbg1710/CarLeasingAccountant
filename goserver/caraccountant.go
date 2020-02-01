package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"

	util "github.com/cbg1710/CarAccountant/util"
)

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hi there, I love %s!", r.URL.Path[1:])
}

func listVehiclesHandler(w http.ResponseWriter, r *http.Request) {
	vehicles := util.GetVehicles()
	json, _ := json.Marshal(vehicles)
	w.Write(json)
}

func main() {
	util.GetVehicles()
	http.HandleFunc("/", handler)
	http.HandleFunc("/listVehicles", listVehiclesHandler)
	log.Fatal(http.ListenAndServe(":8080", nil))
	fmt.Println("Starting the game")
}

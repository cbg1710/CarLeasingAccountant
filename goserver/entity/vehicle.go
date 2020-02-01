package entitiy

import "time"

//Vehicle type
type Vehicle struct {
	Vin  string `json:"vehicle"`
	Name string `json:"name"`
}

// VehicleOverview Complete overview of a vehicle
type VehicleOverview struct {
	Vin     string           `json:"vin"`
	Data    VehicleData      `json:"data"`
	History []VehicleHistory `json:"history"`
}

// VehicleData containig specific vehicle data such as maximum allowd distance
type VehicleData struct {
	Name            string          `json:"name"`
	CurrentOdometer CurrentOdometer `json:"currentOdometer"`
	MaximumDistance int32           `json:"maximumDistance"`
	PickUpDay       time.Time       `json:"pickUpDay"`
	ReturnUpDay     time.Time       `json:"returnUpDay"`
}

// CurrentOdometer odometer and date
type CurrentOdometer struct {
	Odometer    int32     `json:"odometer"`
	OdometerDay time.Time `json:"odometerDay"`
}

// VehicleHistory a odometer on a date
type VehicleHistory struct {
	Date            time.Time `json:"date"`
	Distance        int32     `json:"distance"`
	AllowedDistance float32   `json:"allowedDistance"`
}

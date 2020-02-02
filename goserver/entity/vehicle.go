package entitiy

import (
	"encoding/json"
	"fmt"
	"strconv"
	"time"
)

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
	PickUpDay       Date            `json:"pickUpDay"`
	ReturnDay       Date            `json:"returnDay"`
	MaximumDistance int32           `json:"maximumDistance"`
	CurrentOdometer CurrentOdometer `json:"currentOdometer"`
}

// CurrentOdometer odometer and date
type CurrentOdometer struct {
	Odometer    int32 `json:"odometer"`
	OdometerDay Date  `json:"odometerDay"`
}

// VehicleHistory a odometer on a date
type VehicleHistory struct {
	Date            Date    `json:"date"`
	Distance        int32   `json:"distance"`
	AllowedDistance float32 `json:"allowedDistance"`
}

// Date time for handling like the java LocalDate.class
type Date struct {
	time.Time
}

// MarshalJSON write date as an int array
func (d Date) MarshalJSON() ([]byte, error) {
	result := [3]int{d.Year(), int(d.Month()), d.Day()}
	return json.Marshal(result)
}

// UnmarshalJSON Parses the json string in the custom format
func (d *Date) UnmarshalJSON(b []byte) (err error) {
	var date []int
	json.Unmarshal(b, &date)
	format := "2006-01-02"
	dateStrng := strconv.Itoa(date[0]) + "-" + fmt.Sprintf("%02d", date[1]) + "-" + fmt.Sprintf("%02d", date[2])
	nt, err := time.Parse(format, dateStrng)
	var result Date
	result.Time = nt
	*d = result
	return
}

package util

import (
	"time"

	entitiy "github.com/cbg1710/CarAccountant/entity"
)

var vehicle entitiy.VehicleJSONFile

// GetOverview reuturns the vehicle overview
func GetOverview(vin string) entitiy.VehicleOverview {
	vehicle = GetVehicle(vin)

	var result entitiy.VehicleOverview
	result.ReturnDay = vehicle.Data.ReturnDay
	result.CurrentOdometer = vehicle.Data.CurrentOdometer
	result.MaximumDistance = vehicle.Data.MaximumDistance
	result.RemainingDays = getRemainingDays()
	result.RemainingDistance = getRemainingDistance()
	result.AllowedDistancePerDay = getDistancePerDay()
	result.AverageDistancePerDay = getAverageDistancePerDay()
	result.DistanceDifference = getDistanceDifference()
	return result
}

func getRemainingDays() int32 {
	today := time.Now()
	giveAway := vehicle.Data.ReturnDay.Time
	return getDaysBetween(today, giveAway)
}

func getRemainingDistance() int32 {
	odometer := vehicle.Data.CurrentOdometer.Odometer
	maxDistance := vehicle.Data.MaximumDistance
	return maxDistance - odometer
}

func getDistanceDifference() float32 {
	return getAllowedDistance() - float32(vehicle.Data.CurrentOdometer.Odometer)
}

func getAllowedDistance() float32 {
	return float32(getPassedDays()) * getDistancePerDay()
}

func getDistancePerDay() float32 {
	pickUp := vehicle.Data.PickUpDay.Time
	giveAway := vehicle.Data.ReturnDay.Time

	leasingDays := getDaysBetween(pickUp, giveAway)
	return float32(float32(vehicle.Data.MaximumDistance) / float32(leasingDays))
}

func getAverageDistancePerDay() float32 {
	return float32(float32(vehicle.Data.CurrentOdometer.Odometer) / float32(getPassedDays()))
}

func getPassedDays() int32 {
	today := time.Now()
	pickUp := vehicle.Data.PickUpDay.Time
	return getDaysBetween(pickUp, today)
}

func getDaysBetween(d1 time.Time, d2 time.Time) int32 {
	result := d2.Sub(d1).Hours() / 24
	return int32(result)
}

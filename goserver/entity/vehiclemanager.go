package entity

import (
	"time"
)

// VehicleOverviewBuilder struct for getting a overview
type VehicleOverviewBuilder struct {
	VIN      string
	JSONFile VehicleJSONFile
}

// GetOverview reuturns the vehicle overview
func (b VehicleOverviewBuilder) GetOverview() VehicleOverview {
	var result VehicleOverview
	result.ReturnDay = b.JSONFile.Data.ReturnDay
	result.CurrentOdometer = b.JSONFile.Data.CurrentOdometer
	result.MaximumDistance = b.JSONFile.Data.MaximumDistance
	result.RemainingDays = b.getRemainingDays()
	result.RemainingDistance = b.getRemainingDistance()
	result.AllowedDistancePerDay = b.getDistancePerDay()
	result.AverageDistancePerDay = b.getAverageDistancePerDay()
	result.DistanceDifference = b.getDistanceDifference()
	return result
}

func (b VehicleOverviewBuilder) getRemainingDays() int32 {
	today := time.Now()
	giveAway := b.JSONFile.Data.ReturnDay.Time
	return getDaysBetween(today, giveAway)
}

func (b VehicleOverviewBuilder) getRemainingDistance() int32 {
	odometer := b.JSONFile.Data.CurrentOdometer.Odometer
	maxDistance := b.JSONFile.Data.MaximumDistance
	return maxDistance - odometer
}

func (b VehicleOverviewBuilder) getDistanceDifference() float32 {
	return b.getAllowedDistance() - float32(b.JSONFile.Data.CurrentOdometer.Odometer)
}

func (b VehicleOverviewBuilder) getAllowedDistance() float32 {
	return float32(b.getPassedDays()) * b.getDistancePerDay()
}

func (b VehicleOverviewBuilder) getDistancePerDay() float32 {
	pickUp := b.JSONFile.Data.PickUpDay.Time
	giveAway := b.JSONFile.Data.ReturnDay.Time

	leasingDays := getDaysBetween(pickUp, giveAway)
	return float32(float32(b.JSONFile.Data.MaximumDistance) / float32(leasingDays))
}

func (b VehicleOverviewBuilder) getAverageDistancePerDay() float32 {
	return float32(float32(b.JSONFile.Data.CurrentOdometer.Odometer) / float32(b.getPassedDays()))
}

func (b VehicleOverviewBuilder) getPassedDays() int32 {
	today := time.Now()
	pickUp := b.JSONFile.Data.PickUpDay.Time
	return getDaysBetween(pickUp, today)
}

func getDaysBetween(d1 time.Time, d2 time.Time) int32 {
	result := d2.Sub(d1).Hours() / 24
	return int32(result)
}

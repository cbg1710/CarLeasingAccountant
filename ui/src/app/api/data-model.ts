export class DistanceDay {
  date: string = '';
  distance: number = 0;
  allowedDistance: number = 0;
}

export class Overview {
  name: string = '';
  returnDay: string = '';
  remainingDays: number = 0;
  maximumDistance: number = 0;
  currentOdometer: CurrentOdometer = new CurrentOdometer;
  remainingDistance: number = 0;
  averageDistancePerDay: number = 0;
  allowedDistancePerDay: number = 0;
  distanceDifference: number = 0;
}

export class CurrentOdometer {
  odometerDay: string = null;
  odometer: number = 0;
}

export class Vehicle {
  vehicle: string = '';
  name: string = '';
}

export class NewVehicle {
  maximumDistance: number = 0;
  pickUpDay: string = '';
  returnDay: string = '';
  vin: string = '';
  name: string = '';
}
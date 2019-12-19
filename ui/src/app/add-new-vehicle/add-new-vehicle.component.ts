import { Component, OnInit } from '@angular/core';
import { VehicleService } from '../api/vehicle.service';
import { NewVehicle } from '../api/data-model';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-add-new-vehicle',
  templateUrl: './add-new-vehicle.component.html',
  styleUrls: ['./add-new-vehicle.component.css']
})
export class AddNewVehicleComponent implements OnInit {

  vin: string = '';
  name: string = '';
  pickUpDate: Date = null;
  returnDate: Date = null;
  maxDistance: number;

  constructor(private vehcileService: VehicleService) { }

  ngOnInit() {
  }

  addVehicle() {
    if (this.maxDistance == null || isNaN(this.maxDistance))
      return;
    if (this.isNullOrEmpty(this.vin) || this.isNullOrEmpty(this.name))
      return;
    if (this.pickUpDate == null || this.returnDate == null)
      return;

    var newVehicle: NewVehicle = new NewVehicle();
    newVehicle.maximumDistance = this.maxDistance;
    newVehicle.name = this.name;
    newVehicle.vin = this.vin;

    const format = 'yyyy-MM-dd';
    const locale = 'en-US';

    newVehicle.returnDay = formatDate(this.returnDate, format, locale);
    newVehicle.pickUpDay = formatDate(this.pickUpDate, format, locale);
    this.vehcileService.addVehicle(newVehicle)
      .then(error => console.error(error));

  }

  isNullOrEmpty(s: string): boolean {
    return s == null || s.length == 0;
  }
}

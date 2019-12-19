import { Component, OnInit, ViewChild, Output, Input, EventEmitter } from '@angular/core';
import { VehicleService } from '../api/vehicle.service';

@Component({
  selector: 'app-update-odometer',
  templateUrl: './update-odometer.component.html',
  styleUrls: ['./update-odometer.component.css']
})
export class UpdateOdometerComponent implements OnInit {

  @Input() vin: string;
  @Output() odoChanged = new EventEmitter<string>();
  odo: string;

  constructor(private vehcileService: VehicleService) { }

  ngOnInit() {
  }

  public updateOdo() {
    var newOdo = parseInt(this.odo);
    if (isNaN(newOdo)) {
      alert(this.odo + " is not a number!");
      return;
    }
    console.log("Update odo to " + newOdo);
    this.vehcileService.putOdometer(this.vin, newOdo).then(() => {
      this.odoChanged.emit(this.odo);
    }, error => {
      console.error(error.toString());
    });
  }
}

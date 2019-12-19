import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { VehicleService } from './api/vehicle.service';
import { VehicleComponent } from './vehicle/vehicle.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'CarAccountantUI';

  constructor(
    private router: Router, private vehcileService: VehicleService) {
    this.createDynamic();
  }

  createDynamic() {
    this.vehcileService.listVehicles().then(result => {
      for (let i = 0; i < result.length; i++) {
        let vin = result[i].vehicle;
        let path = vin + '-overview';
        this.router.config.push({ path: path, component: VehicleComponent });
      }
    })
  }
}

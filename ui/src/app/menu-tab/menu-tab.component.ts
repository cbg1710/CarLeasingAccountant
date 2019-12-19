import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { VehicleService } from '../api/vehicle.service';

@Component({
  selector: 'app-menu-tab',
  templateUrl: './menu-tab.component.html',
  styleUrls: ['./menu-tab.component.css']
})
export class MenuTabComponent implements OnInit {

  items: MenuItem[] = [];

  constructor(private vehcileService: VehicleService) { }

  ngOnInit() {
    this.createDynamic();
  }

  createDynamic() {
    this.vehcileService.listVehicles().then(result => {
      for (let i = 0; i < result.length; i++) {
        let name = result[i].name;
        let vin = result[i].vehicle;
        let path = '/' + vin + '-overview';
        this.items.push({ label: name, routerLink: [path, { vin: vin }] });
      }
    });
    this.items.push({ label: 'Add Vehicle', icon: 'pi pi-plus', routerLink: '/add-vehicle' });
  }

}

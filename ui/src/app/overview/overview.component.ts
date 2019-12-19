import { Component, Input, OnInit } from '@angular/core';
import { VehicleService } from '../api/vehicle.service';
import { Overview } from '../api/data-model';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {

  @Input() vin: string;
  overview: Overview = new Overview;

  constructor(private vehcileService: VehicleService) { }

  ngOnInit() {
    this.vehcileService.getOverview(this.vin).then(result => this.overview = result);
  }
}

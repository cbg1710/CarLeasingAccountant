import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OverviewComponent } from '../overview/overview.component';
import { DistanceChartComponent } from '../distance-chart/distance-chart.component';

@Component({
  selector: 'app-vehicle',
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.css']
})
export class VehicleComponent implements OnInit {
  vin: string = '';
  @ViewChild(DistanceChartComponent, { static: false }) private chart: DistanceChartComponent;
  @ViewChild(OverviewComponent, { static: false }) private overview: OverviewComponent;

  constructor(route: ActivatedRoute) {
    route.paramMap.subscribe(paramMap => this.vin = paramMap.get('vin'));
    console.log(this.vin);
  }

  ngOnInit() {
  }

  updateComponents(odo: string) {
    this.chart.ngOnInit();
    this.overview.ngOnInit();
  }

}

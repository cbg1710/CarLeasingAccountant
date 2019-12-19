import { Component, OnInit, Input } from '@angular/core';
import { DistanceDay } from '../api/data-model'
import { VehicleService } from '../api/vehicle.service';
import { log } from "util";

@Component({
  selector: 'app-distance-chart',
  templateUrl: './distance-chart.component.html',
  styleUrls: ['./distance-chart.component.css']
})
export class DistanceChartComponent implements OnInit {

  @Input() vin: string;

  dataPoints = [{ label: '10', value: 10 }, { label: '20', value: 20 }, { label: '30', value: 30 }, { label: 'All', value: 0 }];
  dataPointsSelected: number = 10;
  distances: DistanceDay[] = null;
  errorText: string = null;
  data: any;

  constructor(private vehcileService: VehicleService) { }

  ngOnInit(): void {
    this.getDistances();
  }

  private getDistances() {
    log('Get distances ...');
    this.initData();
  }

  private initData() {
    this.vehcileService.getDistances(this.vin, this.dataPointsSelected).then(result => {
      this.initDataImpl(result);
      this.errorText = null;
    }, error => {
      this.errorText = error.toString();
      console.error(this.errorText);
    });
  }

  private initDataImpl(distanceDays: DistanceDay[]) {
    this.data = {
      labels: distanceDays.map(d => d.date),
      datasets: [
        {
          label: 'Positive Distance difference',
          backgroundColor: '#39b500',
          data: distanceDays.map(d => {
            var diff = d.allowedDistance - d.distance;
            if (diff >= 0) {
              return diff;
            }
          })
        },
        {
          label: 'Negative Distance difference',
          backgroundColor: '#ed0909',
          data: distanceDays.map(d => {
            var diff = d.allowedDistance - d.distance;
            if (diff < 0) {
              return diff;
            }
          })
        },
      ],
      width: '100%'
    };
  }

  public handleDataPointsCountChange(e: any) {
    this.initData();
  }
}

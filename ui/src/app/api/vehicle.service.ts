import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DistanceDay, Overview, Vehicle, NewVehicle } from './data-model';

@Injectable({ providedIn: 'root' })
export class VehicleService {

  constructor(private httpClient: HttpClient) { }

  public getDistances(vin: string, dataEntries: number): Promise<DistanceDay[]> {
    var query = '?vin=' + vin;
    if (dataEntries != null || dataEntries > 0) {
      query += '&dataPoints=' + dataEntries;
    }
    return this.httpClient.get<DistanceDay[]>(environment.distanceHistory + query).toPromise();
  }

  public putOdometer(vin: string, odo: number): Promise<any> {
    var query = '?vin=' + vin + '&odo=' + odo;
    return this.httpClient.post<any>(environment.updateOdo + query, null).toPromise();
  }

  public getOverview(vin: string): Promise<Overview> {
    var query = '?vin=' + vin;
    return this.httpClient.get<Overview>(environment.overview + query).toPromise();
  }

  public listVehicles(): Promise<Vehicle[]> {
    return this.httpClient.get<Vehicle[]>(environment.listVehicles).toPromise();
  }

  public addVehicle(newVehicle: NewVehicle): Promise<any> {
    return this.httpClient.post<any>(environment.newVehicle, newVehicle).toPromise();
  }
}
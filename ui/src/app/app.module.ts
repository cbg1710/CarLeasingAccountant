import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DistanceChartComponent } from './distance-chart/distance-chart.component';

import { ButtonModule } from 'primeng/button';
import { SelectButtonModule } from 'primeng/selectbutton';
import { TabMenuModule } from 'primeng/tabmenu';
import { InputTextModule } from 'primeng/components/inputtext/inputtext';
import { ChartModule } from 'primeng/chart'
import { MenuModule } from 'primeng/menu';
import { MenuTabComponent } from './menu-tab/menu-tab.component';
import { OverviewComponent } from './overview/overview.component';
import { UpdateOdometerComponent } from './update-odometer/update-odometer.component';
import { VehicleComponent } from './vehicle/vehicle.component';
import { AddNewVehicleComponent } from './add-new-vehicle/add-new-vehicle.component';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CalendarModule } from 'primeng/calendar';

const routes: Routes = [
  { path: '', redirectTo: 'WDD1770441J099374-overview;vin=WDD1770441J099374', pathMatch: 'full' },  // Start page
  {
    path: 'WDD1770441J099374-overview', component: VehicleComponent
  },
  {
    path: 'add-vehicle', component: AddNewVehicleComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    DistanceChartComponent,
    MenuTabComponent,
    OverviewComponent,
    UpdateOdometerComponent,
    VehicleComponent,
    AddNewVehicleComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,

    AppRoutingModule,
    RouterModule.forRoot(routes, { useHash: true }),

    InputTextModule,
    MenuModule,
    ButtonModule,
    SelectButtonModule,
    TabMenuModule,
    ChartModule,
    CalendarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

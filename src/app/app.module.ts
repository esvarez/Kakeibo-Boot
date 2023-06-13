import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { NgChartsModule } from 'ng2-charts';

import { AppComponent } from './app.component';
import { ChartsComponent } from './charts/charts.component';
import { MovementsComponent } from './movements/movements.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AddMovementComponent } from './add-movement/add-movement.component';
import { SumaryComponent } from './sumary/sumary.component';
import { IndexComponent } from './pages/index/index.component';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    ChartsComponent,
    MovementsComponent,
    NavbarComponent,
    AddMovementComponent,
    SumaryComponent,
    IndexComponent
  ],
  imports: [
    BrowserModule,
    NgChartsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

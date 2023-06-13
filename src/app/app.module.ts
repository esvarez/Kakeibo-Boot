import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgChartsModule } from 'ng2-charts';

import { AppComponent } from './app.component';
import { ChartsComponent } from './charts/charts.component';
import { MovementsComponent } from './movements/movements.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AddMovementComponent } from './add-movement/add-movement.component';
import { SumaryComponent } from './sumary/sumary.component';
import { IndexComponent } from './pages/index/index.component';
import { AppRoutingModule } from './app-routing.module';
import { NewMovementComponent } from './pages/new-movement/new-movement.component';
import { MovementFormComponent } from './components/movement-form/movement-form.component';
import { SelectInputComponent } from './shared/components/select-input/select-input.component';

@NgModule({
  declarations: [
    AppComponent,
    ChartsComponent,
    MovementsComponent,
    NavbarComponent,
    AddMovementComponent,
    SumaryComponent,
    IndexComponent,
    NewMovementComponent,
    MovementFormComponent,
    SelectInputComponent
  ],
  imports: [
    BrowserModule,
    NgChartsModule,
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

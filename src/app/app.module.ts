import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { registerLocaleData } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {RouterOutlet} from "@angular/router";
import localeEs from '@angular/common/locales/es';

import { NgChartsModule } from 'ng2-charts';

import { AppComponent } from './app.component';
import { ChartsComponent } from './components/charts/charts.component';
import { MovementsComponent } from './components/movements/movements.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AddMovementComponent } from './components/add-movement/add-movement.component';
import { SumaryComponent } from './components/sumary/sumary.component';
import { IndexComponent } from './pages/index/index.component';
import { AppRoutingModule } from './app-routing.module';
import { NewMovementComponent } from './pages/new-movement/new-movement.component';
import { MovementFormComponent } from './components/movement-form/movement-form.component';
import { SelectInputComponent } from './shared/components/select-input/select-input.component';
import { ComboboxComponent } from './shared/components/combobox/combobox.component';
import { LoginPage } from './pages/login-page/login.page';
import { AppPage } from './pages/app-page/app.page';

registerLocaleData(localeEs, 'es');

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
    SelectInputComponent,
    ComboboxComponent,
    LoginPage,
    AppPage
  ],
  imports: [
    BrowserModule,
    NgChartsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    RouterOutlet
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

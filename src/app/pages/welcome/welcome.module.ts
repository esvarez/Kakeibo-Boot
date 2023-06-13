import { NgModule } from '@angular/core';
import { NgForOf, NgIf } from "@angular/common";

import { WelcomeRoutingModule } from './welcome-routing.module';

import { WelcomeComponent } from './welcome.component';
import { MovementsComponent } from 'src/app/components/movements/movements.component';
import { IndexComponent } from 'src/app/pages/index/index.component';
import { NzListModule } from 'ng-zorro-antd/list';
import { NzSkeletonModule } from 'ng-zorro-antd/skeleton';

@NgModule({
  imports: [WelcomeRoutingModule, NzListModule, NzSkeletonModule, NgForOf, NgIf],
  declarations: [WelcomeComponent, IndexComponent, MovementsComponent],
  exports: [WelcomeComponent, IndexComponent]
})
export class WelcomeModule { }

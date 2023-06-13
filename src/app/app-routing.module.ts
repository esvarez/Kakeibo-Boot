import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {IndexComponent} from "./pages/index/index.component";
import {NewMovementComponent} from "./pages/new-movement/new-movement.component";

const routes: Routes = [
  {path: '', component: IndexComponent},
  {path:'movement', children: [
      {path: 'create', component: NewMovementComponent},
    ] }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

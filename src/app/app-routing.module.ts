import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {IndexComponent} from "./pages/index/index.component";
import {NewMovementComponent} from "./pages/new-movement/new-movement.component";
import {EditMovementComponent} from "./pages/edit-movement/edit-movement.component";
import {AccountsComponent} from "./pages/accounts/accounts.component";

const routes: Routes = [
  {path: '', component: IndexComponent},
  {
    path:'movement', children: [
      {path: 'create', component: NewMovementComponent},
      {path: 'edit/:id', component: EditMovementComponent}
    ]
  },
  {
    path: 'account', children: [
      { path: '', component: AccountsComponent}
      ]
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

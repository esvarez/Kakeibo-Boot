import { Component } from '@angular/core';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent {
  accounts: any[] = [
    {
      name: 'Checking',
      total: 1000.00
    },
    {
      name: 'Savings',
      total: 2000.00
    },
    ]

}

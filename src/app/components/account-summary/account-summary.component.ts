import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-account-summary',
  templateUrl: './account-summary.component.html',
  styleUrls: ['./account-summary.component.css']
})
export class AccountSummaryComponent {
  @Input() accountName: string
  @Input() total: number

  constructor() {
    this.total = 0
    this.accountName = 'Account'
  }
}

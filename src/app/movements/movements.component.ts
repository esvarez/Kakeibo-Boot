import { Component } from '@angular/core';

interface Movement {
  date: string;
  description: string;
  amount: number;
  category: string;
}
@Component({
  selector: 'app-movements',
  templateUrl: './movements.component.html',
  styleUrls: ['./movements.component.css']
})
export class MovementsComponent {

  // Create interface for movements
  movements: Movement[] = [
    {
      date: '2021-07-01',
      description: 'Income',
      amount: 1000,
      category: '1 Salary'
    },
    {
      date: '2021-07-02',
      description: 'Rent',
      amount: -500,
      category: '2 Housing'
    },
    {
      date: '2021-07-03',
      description: 'Groceries',
      amount: -100,
      category: '3 Food'
    },
    {
      date: '2021-07-04',
      description: 'Income',
      amount: 1000,
      category: '4 Salary'
    },
    {
      date: '2021-07-05',
      description: 'Rent',
      amount: -500,
      category: '5 Housing'
    }
  ]


}

import { Component } from '@angular/core';

@Component({
  selector: 'app-date-picker',
  templateUrl: './date-picker.component.html',
  styleUrls: ['./date-picker.component.css']
})
export class DatePickerComponent {
  daysLastMonth: number[]
  daysThisMonth: number[]
  daysNextMonth: number[]
  today: number = new Date().getDate()
  currentMonth: number = new Date().getMonth()
  isCurrentMonth: boolean = true
  monthNames: string[] = [
    'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Augosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
  ]
  month: number
  isSelected: boolean = false

  firstDayOfTheWeek: number
  lastDayOfTheWeek: number
  firstDayLastWeek: number
  lastDayLastWeek: number
  dateToShow = new Date();

  constructor() {
    this.daysLastMonth = []
    this.daysThisMonth = []
    this.daysNextMonth = []
    this.month = 0
    this.lastDayOfTheWeek = 0
    this.firstDayOfTheWeek = 0
    this.firstDayLastWeek = 0
    this.lastDayLastWeek = 0
    this.getCurrentMonthDays()

  }

  getDaysInMonth(year: number, month: number): number {
    return new Date(year, month + 1, 0).getDate();
  }

  getCurrentMonthDays() {
    // if (this.dateToShow === undefined) {
    //   this.dateToShow = new Date();
    // }
    const currentDate = this.dateToShow;
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth();
    this.isCurrentMonth = currentMonth === this.currentMonth

    const daysInMonth = this.getDaysInMonth(currentYear, currentMonth);
    this.daysThisMonth = Array.from({ length: daysInMonth }, (_, index) => index + 1);

    const firstWeek = new Date(currentYear, currentMonth, 1);
    this.firstDayOfTheWeek = firstWeek.getDay() === 0 ? 1 : 0
    this.lastDayOfTheWeek = 7 - firstWeek.getDay()

    const lastWeek = new Date(currentYear, currentMonth, daysInMonth);
    this.firstDayLastWeek = daysInMonth - lastWeek.getDay()
    this.lastDayLastWeek = lastWeek.getDay() === 6 ? daysInMonth: 0

    const lastMonth = new Date(currentYear, currentMonth, 1);
    const daysInLastMonth = lastMonth.getDay();
    const daysCounter = this.getDaysInMonth(currentYear, currentMonth-1);
    let startDay = daysCounter - daysInLastMonth + 1
    this.daysLastMonth = Array.from({ length: daysInLastMonth }, (_, index) => index + startDay);

    const nextMonth = new Date(currentYear, currentMonth, daysInMonth);
    const daysInNextMonth = 6 - nextMonth.getDay();
    this.month = currentMonth
    this.daysNextMonth = Array.from({ length: daysInNextMonth }, (_, index) => index + 1);
  }

  // TODO get first day of the first week of the month
  // TODO get last day of the first week of the month
  // TODO get first day of the last week of the month
  // TODO get last day of the last week of the month

  nextMonth() {
    this.dateToShow = new Date(this.dateToShow.getFullYear(), this.dateToShow.getMonth() + 1, 1);
    this.getCurrentMonthDays()
  }

  previousMonth() {
    this.dateToShow = new Date(this.dateToShow.getFullYear(), this.dateToShow.getMonth() - 1, 1);
    this.getCurrentMonthDays()
  }
}

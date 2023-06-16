import { Component, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

import DataLabelsPlugin from 'chartjs-plugin-datalabels';

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  public barChartData: ChartData<'bar'> = {
    labels: [ 'Julio', 'Agosto', 'Septiembre', 'Octubre'],
    datasets: [
      {
        label: 'Ingresos',
        data: [ 100, 59, 80, 2 ],
        backgroundColor: '#92D050',
      },
      {
        label: 'Gastos',
        data: [ 28, 48, 40,11 ],
        backgroundColor: '#F08080',
      },
      {
        label: 'Ahorro',
        data: [ 28, 48, 40, -90 ],
        backgroundColor: '#FFD700',
      }
    ]
  };
}

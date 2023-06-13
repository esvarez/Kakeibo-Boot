import {Component} from '@angular/core';
import {SelectItem} from "../../shared/contracts";

@Component({
  selector: 'app-movement-form',
  templateUrl: './movement-form.component.html',
  styleUrls: ['./movement-form.component.css'],

})
export class MovementFormComponent {

  movementsLabel: string = 'Tipo de movimiento'
  movementsOptions: SelectItem[] = [
    {id: 1, name: 'Gasto'},
    {id: 2, name: 'Ingreso'},
    {id: 3, name: 'Transferencia'}
  ]

  accountsLabel: string = 'Cuenta'
  accountsLabelTransfer: string = 'Cuenta de destino'
  accountsOptions: SelectItem[] = [
    {id: 1, name: 'Efectivo'},
    {id: 2, name: 'Tarjeta de crédito'},
    {id: 3, name: 'Cuenta bancaria'}
  ]

}

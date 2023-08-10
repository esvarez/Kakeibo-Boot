import {Component, Input} from '@angular/core';
import {Movement, MovementTypes, SelectItem} from "../../shared/contracts";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-movement-form',
  templateUrl: './movement-form.component.html',
  styleUrls: ['./movement-form.component.css'],

})
export class MovementFormComponent {
  @Input() movement: Movement | undefined
  isDateFocused: boolean = false

  movementForm = new FormGroup({
    amount: new FormControl('',[
      Validators.required,
      Validators.min(1),
    ]),
    movementType: new FormGroup({
      id: new FormControl('', Validators.required),
      name: new FormControl('', Validators.required),
    })
  })

  constructor() {
    if (this.movement) {
      this.movementForm.patchValue({
        amount: this.movement.amount.toString(),
      })
    }
  }

  get amount() {
    return this.movementForm.get('amount')
  }

  get movementType() {
    return this.movementForm.get('movementType')
  }

  get movementTypeId() {
    return this.movementForm.get('movementType.id')
  }

  get isAmountInvalid(): boolean | undefined {
    return this.amount?.invalid && (this.amount?.dirty || this.amount?.touched)
  }

  movementsLabel: string = 'Tipo de movimiento'
  movementsOptions: SelectItem[] = [
    {id: MovementTypes.Expense, name: 'Gasto'},
    {id: MovementTypes.Income, name: 'Ingreso'},
    {id: MovementTypes.Transfer, name: 'Transferencia'}
  ]

  accountsLabel: string = 'Cuenta'
  accountsLabelTransfer: string = 'Cuenta de destino'
  accountsOptions: SelectItem[] = [
    {id: 1, name: 'Efectivo'},
    {id: 2, name: 'Tarjeta de crédito'},
    {id: 3, name: 'Cuenta bancaria'}
  ]

  onSubmit() {
    this.movementForm.markAllAsTouched()
    console.log(this.movementForm.value)
  }

  onDateFocus() {
    this.isDateFocused = true
  }
  onDateBlur() {
    this.isDateFocused = false
  }

}

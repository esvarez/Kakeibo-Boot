import {Component, Input} from '@angular/core';

import {SelectItem} from "../../contracts";
import {timeout} from "rxjs";

@Component({
  selector: 'app-select-input',
  templateUrl: './select-input.component.html',
  styleUrls: ['./select-input.component.css']
})
export class SelectInputComponent {
  @Input() selectItems: SelectItem[] | undefined
  @Input() itemSelected: SelectItem
  @Input() label: string | undefined
  isSelectOpen: boolean
  highlightedIndex: number | null
  isHidden: boolean

  constructor() {
    this.itemSelected = {id: 0, name: ''}
    this.isSelectOpen = false
    this.highlightedIndex = null
    this.isHidden = true
  }

  selectItem(selectItem: SelectItem) {
    this.itemSelected = selectItem;
    this.isSelectOpen = false;
    this.hideOptions()
  }

  toggleSelect() {
    this.isSelectOpen = !this.isSelectOpen;
    this.isHidden = this.isSelectOpen? false : true
  }

  hideOptions() {
    setTimeout(() =>  this.isHidden = true, 110)
  }
}

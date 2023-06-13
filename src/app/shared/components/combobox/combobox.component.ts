import { Component } from '@angular/core';
import {SelectItem} from "../../contracts";
import {filter} from "rxjs";

@Component({
  selector: 'app-combobox',
  templateUrl: './combobox.component.html',
  styleUrls: ['./combobox.component.css']
})
export class ComboboxComponent {

  label: string = 'Categoria';
  items: SelectItem[] = [
    {id: 1, name: 'Carro'},
    {id: 2, name: 'Moto'},
    {id: 3, name: 'Renta'},
    {id: 4, name: 'Casa'},
    {id: 5, name: 'Restaurante'},
    {id: 6, name: 'Hotel'},
  ];
  itemsFiltered: SelectItem[] = this.items;
  itemSelected: SelectItem = {id: 0, name: ''};

  isSelectOpen: boolean
  highlightedIndex: number | null
  isHidden: boolean
  value: string = ''

  existCategory: boolean = false


  constructor() {
    this.isSelectOpen = false
    this.highlightedIndex = null
    this.isHidden = true
  }

  openSelect() {
    this.isSelectOpen = true
    this.isHidden = false
    this.filter(this.value)
  }

  selectItem(selectItem: SelectItem) {
    this.itemSelected = selectItem;
    this.value = this.itemSelected.name
    this.itemsFiltered = this.items.filter((item) => item.name.toLowerCase().includes(this.value));
    // this.hideOptions()
  }

  hideOptions() {
    setTimeout(() =>  this.isHidden = true, 110)
  }

  filterItems(event: any) {
    const value = event.target.value.toLowerCase();
    this.filter(value)
  }

  filter(value: string) {
    this.itemsFiltered = this.items.filter((item) => item.name.toLowerCase().includes(value.toLowerCase()));
    if (this.itemsFiltered.length === 1 &&
      this.itemsFiltered[0].name.toLowerCase() === value.toLowerCase()) {
      this.existCategory = true
    } else {
      this.existCategory = false
    }
  }

  newCategory(value: string) {
    this.value = value
  }
}

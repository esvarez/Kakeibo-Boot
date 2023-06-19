import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-movement',
  templateUrl: './edit-movement.component.html',
  styleUrls: ['./edit-movement.component.css']
})
export class EditMovementComponent {
  id: string | null

  constructor(private route: ActivatedRoute) {
    this.id = this.route.snapshot.paramMap.get('id')
  }

}

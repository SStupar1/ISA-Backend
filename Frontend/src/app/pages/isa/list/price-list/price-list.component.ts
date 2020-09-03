import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AppointmentTypeService } from 'src/app/services/appointment-type.service';

@Component({
  selector: 'app-price-list',
  templateUrl: './price-list.component.html',
  styleUrls: ['./price-list.component.scss']
})
export class PriceListComponent implements OnInit {

  public listOfData = [];

  constructor(private appointmentTypeService: AppointmentTypeService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.setupData();
  }
  
  private setupData(): void {  
    this.appointmentTypeService.getAllAppointmentType().subscribe(data => {
      this.listOfData = data;
    });
  }

}

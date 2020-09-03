import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.scss']
})
export class RequestListComponent implements OnInit {

  public listOfData = [];

  constructor(private requestService: RequestService , private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.setupData();
  }


  private setupData(): void {
    this.requestService.getAllRequests().subscribe(data => {
      this.listOfData = data;
      //console.log(data);
    });
  }

  public approveRequest(id): void {
    this.requestService.approveRequest(id).subscribe(data => {
      //this.router.navigateByUrl("dashboard/registration-request");
      this.setupData();
    });
    // this.setupData();
    // window.location.reload();
  }

  public denyRequest(id): void {
    this.requestService.denyRequest(id).subscribe(data => {
      //this.router.navigateByUrl("dashboard/registration-request");
      this.setupData();
    });
    // this.setupData();
    // window.location.reload();
  }

}

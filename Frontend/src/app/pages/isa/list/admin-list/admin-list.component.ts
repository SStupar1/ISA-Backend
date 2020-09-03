import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-list',
  templateUrl: './admin-list.component.html',
  styleUrls: ['./admin-list.component.scss']
})
export class AdminListComponent implements OnInit {

  public listOfData = [];


  constructor(private adminService: AdminService, private router: Router) { }

  ngOnInit() {
    this.setupData();
  }

  private setupData(): void {
    this.adminService.getAllAdmins().subscribe(data => {
      this.listOfData = data;
    });
  }

  onProfil(id) {
    this.router.navigateByUrl(`dashboard/admin-profile/${id}`);
  }

  onDelete(id): void {
    this.adminService.deleteAdmin(id).subscribe(() => {
      this.setupData();
    })
  }
}

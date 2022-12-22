import { Component } from '@angular/core';
import {UserRecordService} from "../service/user-record.service";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {CreateRecordComponent} from "../create-record/create-record.component";
import {AdminService} from "../service/admin.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  loading: boolean = false;
  result?: any;

  constructor(private adminService: AdminService, public router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.adminService.getUsers()
      .subscribe(
        (data: any) => {
          this.result = data;
        },
        (err: any) => {
          this.router.navigateByUrl('/logon');
        },
        () => {
          this.loading = false;
        });
  }

  reloadRecords() {
    this.loading = true;
    this.adminService.getUsers()
      .subscribe(
        (data: any) => {
          this.result = data;
        },
        (err: any) => {
          this.router.navigateByUrl('/logon');
        },
        () => {
          this.loading = false;
        });
  }

  deleteRecord(id: any) {
    if (window.confirm('Are you sure?')) {
      this.adminService.deleteUser(id)
        .subscribe(
          () => {
            this.onDeleteComplete();
          },
          (error: any) => {

          }
        )
    }
  }



  private onDeleteComplete() {
    this.reloadRecords();
  }

}

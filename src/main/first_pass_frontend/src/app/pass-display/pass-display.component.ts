import { Component } from '@angular/core';
import {UserRecordService} from "../service/user-record.service";
import {MatDialog} from "@angular/material/dialog";
import {CreateRecordComponent} from "../create-record/create-record.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-pass-display',
  templateUrl: './pass-display.component.html',
  styleUrls: ['./pass-display.component.css']
})
export class PassDisplayComponent {

  loading: boolean = false;
  result?: any;

  constructor(private userRecordService: UserRecordService, public dialog: MatDialog, public router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.userRecordService.getUserRecords()
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
    this.userRecordService.getUserRecords()
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
      this.userRecordService.deleteRecord(id)
        .subscribe(
          () => {
            this.onDeleteComplete();
          },
          (error: any) => {

          }
        )
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(CreateRecordComponent, {
      width: '1000px'

    });

    dialogRef.afterClosed().subscribe( data => {
        this.saveRecord(data)
    });
  }

  private saveRecord( data:any ) {
    this.userRecordService.saveRecord(data)
      .subscribe(
        () => {},
        (err: any) => {

        },
        () => {
          this.reloadRecords();
        }

      );
  }

  private onDeleteComplete() {
    this.reloadRecords();
  }
}


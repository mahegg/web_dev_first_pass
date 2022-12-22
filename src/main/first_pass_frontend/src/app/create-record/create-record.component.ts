import {Component, Inject} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import { Router } from '@angular/router';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-create-record',
  templateUrl: './create-record.component.html',
  styleUrls: ['./create-record.component.css']
})
export class CreateRecordComponent {

  recordForm!: FormGroup;
  result = {
    username: '',
    url: ''
  };

  constructor(private router: Router, private formBuilder: FormBuilder, @Inject(MAT_DIALOG_DATA) public data:any,
              public dialogRef: MatDialogRef<CreateRecordComponent>) {}


  ngOnInit(): void {
    this.recordForm = this.formBuilder.group( {
      username: new FormControl(),
      url: new FormControl()
    })
  }

  updateFormValues() {
    this.result.username = this.recordForm.value.username;
    this.result.url = this.recordForm.value.url;
    this.dialogRef.close(this.result);
  }

  onNoClick() {
    this.recordForm.reset();
    this.dialogRef.close();
  }

}

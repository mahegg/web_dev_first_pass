import { Component } from '@angular/core';
import {AuthenticateService} from "../service/authenticate.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {StorageService} from "../service/storage.service";
import {IUser} from "../model/user.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  authForm!: FormGroup;
  isSubmitted  =  false;
  result?: IUser;

  constructor(private authService: AuthenticateService, private storageService: StorageService,
              private router: Router, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.authForm  =  this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get formControls() { return this.authForm.controls; }

  signIn(){
    this.isSubmitted = true;
    if(this.authForm.invalid){
      return;
    }
    this.authService.login(this.authForm.get('username')?.value, this.authForm.get('password')?.value)
      .subscribe(
      (data: any) => {
        this.result = data.body;
      },
        (err:any) => {
          this.router.navigateByUrl('/logon');
        },
        () => {
          this.storageService.saveUser(this.result)
          this.router.navigateByUrl('/');
    });
  }

  cancel() {
    this.router.navigate(['/logon']);
  }

}

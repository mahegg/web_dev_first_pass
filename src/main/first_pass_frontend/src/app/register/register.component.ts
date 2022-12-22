import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup, FormGroupDirective, NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators
} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../service/user.service";
import {AuthenticateService} from "../service/authenticate.service";
import {ErrorStateMatcher} from "@angular/material/core";
import {StorageService} from "../service/storage.service";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent {
  registerForm!: FormGroup;
  isSubmitted  =  false;
  matcher = new MyErrorStateMatcher();
  result = {
    username: '',
    email: '',
    password: ''
  };
  errorMap: any;

  constructor(private authService: AuthenticateService,
              private router: Router, private fb: FormBuilder) {}

  ngOnInit() {
    this.registerForm = this.fb.group(
      {
        username: ["", Validators.required],
        email: ["", Validators.required],
        passGroup: this.fb.group({
          password: ['', [Validators.required]],
          confirmPassword: ['']
        }, { validators: this.checkPasswords })
      });
  }

  checkPasswords: ValidatorFn = (group: AbstractControl):  ValidationErrors | null => {
    let pass = group.get('password').value;
    let confirmPass = group.get('confirmPassword').value
    return pass === confirmPass ? null : { notSame: true }
  }

  register() {
    this.isSubmitted = true;
    if(this.registerForm.invalid){
      return;
    }
    this.authService.register(this.registerForm.get('username')?.value, this.registerForm.get('email')?.value,
      this.registerForm.get('passGroup.password')?.value)
      .subscribe(
        (data: any) => {
          this.result = data.body;
        },
        (err:any) => {
          console.log(err);
          this.errorMap = err.error;
        },
        () => {
          this.router.navigateByUrl('/logon');
        });
  }
}


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control?.invalid && control?.parent?.dirty);
    const invalidParent = !!(control?.parent?.invalid && control?.parent?.dirty);

    return invalidCtrl || invalidParent;
  }
}

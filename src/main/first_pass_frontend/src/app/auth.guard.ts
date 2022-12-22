import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {StorageService} from "./service/storage.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private storageService: StorageService, private router: Router) {

}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let url: string = state.url;
    return this.checkUserLogin(route, url);
  }

  checkUserLogin(route: ActivatedRouteSnapshot, url: any): boolean {
    const authorities = route.data['authorities']
    if (this.storageService.isLoggedIn()) {
      const roles = this.storageService.getUser().roles;
      const found = authorities.some((r: any) => roles.indexOf(r) >= 0);
      if (found) {
        return true;
      }
      return false;
    }
    this.router.navigate(['/logon']);
    return false;
  }

}

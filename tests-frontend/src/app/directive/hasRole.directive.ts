import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { IUser } from '../model/user';
import { UserService } from '../services/user.service';

@Directive({
  selector: '[appHasRole]',
})
export class HasRoleDirective {
  currentUser!: IUser;
  requiredRoles!: string[];
  isVisible = false;


  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.userService.currentUser.subscribe((user) => {
      this.currentUser = user;
      this.updateView();
    });
  }

  @Input()
  set appHasRole(roles: string[]) {
    this.requiredRoles = roles;
    this.updateView();
  }

  private updateView() {
    if (this.hasRole()) {
      if (!this.isVisible) {
        this.isVisible = true;
        this.viewContainer.createEmbeddedView(this.templateRef);
      }
    } else {
      this.isVisible = false;
      this.viewContainer.clear();
    }
  }

  private hasRole(): boolean {
    if (this.currentUser && this.currentUser.roles && this.requiredRoles) {
      return this.currentUser.roles.some((role) =>
        this.requiredRoles.includes(role.authority)
      );
    }
    return false;
  }
}

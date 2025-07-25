import { Directive, inject, TemplateRef, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appIfAdmin]',
})
export class IfAdminDirective {
  private readonly _viewContainerRef = inject(ViewContainerRef);
  private readonly _templateRef = inject(TemplateRef);

  private _hasView = false;

  constructor() {
    this.handleView();
  }

  private handleView(): void {
    const isAdmin = true; //Just for testing
    if (isAdmin && !this._hasView) {
      this._viewContainerRef.createEmbeddedView(this._templateRef);
      this._hasView = true;
    } else if (!isAdmin && this._hasView) {
      this._viewContainerRef.clear();
      this._hasView = false;
    }
  }
}

import {
  Directive,
  ElementRef,
  HostBinding,
  Input,
  Renderer2,
} from '@angular/core';
// used to change dom use appWrongInput or [appWrongInput] on any dom element to have an effect on it
@Directive({
  selector: '[appWrongInput]',
  standalone: true,
})
export class WrongInputDirective {
  // ignore renderer its the dom manipulation method not recommended
  constructor(private elementRef: ElementRef, private renderer: Renderer2) {}

  //only then can we do [appWrongInput]="formCondition"
  @Input() set appWrongInput(condition: boolean) {
    if (condition) {
      this.styles = { boxShadow: '0 0 1px 2px red inset' };
    } else {
      this.styles = { boxShadow: '0 0 1px 2px white inset' };
    }
  }

  // bides this to an elements property on the dom
  //@HostBinding('style') 'style' -> take the style Attribute
  // change its boxShadow property
  @HostBinding('style') styles = {
    boxShadow: '0 0 1px 2px white inset',
  };

  // can set multiple properties
  // @HostBinding('style') styles = {
  //   boxShadow: '0 0 5px rgba(0, 0, 0, 0.3)',
  //       color: 'white',
  //  backgroundColor: 'red',
  // };

  //you can have multiple host bindings
  //@HostBinding('style.backgroundColor') backgroundColor: string = 'yellow';
}

import {
  Directive,
  ElementRef,
  HostBinding,
  Input,
  Renderer2,
} from '@angular/core';
// used to change dom
@Directive({
  selector: '[appWrongInput]',
  standalone: true,
})
export class WrongInputDirective {
  constructor(private elementRef: ElementRef, private renderer: Renderer2) {}

  @Input() set appWrongInput(condition: boolean) {
    if (condition) {
      this.styles = { boxShadow: '0 0 1px 2px red inset' };
    }
  }

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

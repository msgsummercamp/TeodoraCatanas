import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'maskPassword',
})
export class MaskPasswordPipe implements PipeTransform {
  transform(password: string, maskChar: string = '*'): string {
    return maskChar.repeat(password.length);
  }
}

import { CanDeactivateFn } from '@angular/router';

export const confirmExitGuard: CanDeactivateFn<unknown> = () => {
  return confirm('Are you really really !!REALLY!! sure you want to leave :( ?');
};

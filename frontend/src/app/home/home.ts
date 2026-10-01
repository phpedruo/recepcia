import { httpResource } from '@angular/common/http';
import { Component } from '@angular/core';

interface Health {
  status: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.html',
})
export class Home {
  protected readonly health = httpResource<Health>(() => '/actuator/health');
}

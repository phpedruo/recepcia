import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Home } from './home';

describe('Home', () => {
  let fixture: ComponentFixture<Home>;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [Home],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    fixture = TestBed.createComponent(Home);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  function text(): string {
    return (fixture.nativeElement as HTMLElement).textContent ?? '';
  }

  async function answerHealthCheck(body: object, status: number, statusText: string) {
    fixture.detectChanges();
    http.expectOne('/actuator/health').flush(body, { status, statusText });
    await fixture.whenStable();
  }

  it('says it is checking while the API has not answered', () => {
    fixture.detectChanges();

    expect(text()).toContain('Verificando a API');

    http.expectOne('/actuator/health').flush({ status: 'UP' });
  });

  it('shows the status the API reports', async () => {
    await answerHealthCheck({ status: 'UP' }, 200, 'OK');

    expect(text()).toContain('UP');
  });

  it('says the API is down when the health check fails', async () => {
    await answerHealthCheck({ status: 'DOWN' }, 503, 'Service Unavailable');

    expect(text()).toContain('fora do ar');
  });
});

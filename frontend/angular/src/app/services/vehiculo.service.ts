import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { catchError, finalize, Observable, tap, throwError } from 'rxjs';
import { CreateVehiculo, Vehiculo } from '../models/vehiculo.model';

@Injectable({ providedIn: 'root' })
export class VehiculoService {
  private readonly apiUrl = 'http://localhost:8087/api/vehiculos';
  private http = inject(HttpClient);

  private readonly loading = signal(false);
  private readonly error = signal<string | null>(null);
  private readonly data = signal<Vehiculo[]>([]);

  readonly isLoading = this.loading.asReadonly();
  readonly requestError = this.error.asReadonly();
  readonly vehicles = this.data.asReadonly();

  getAll(): Observable<Vehiculo[]> {
    return this.request(
      this.http.get<Vehiculo[]>(this.apiUrl).pipe(
        tap((vehicles) => this.data.set(vehicles))
      )
    );
  }

  getById(id: number): Observable<Vehiculo> {
    return this.request(this.http.get<Vehiculo>(`${this.apiUrl}/${id}`));
  }

  create(data: CreateVehiculo): Observable<Vehiculo> {
    return this.request(
      this.http.post<Vehiculo>(this.apiUrl, data)
    );
  }

  update(id: number, data: CreateVehiculo): Observable<Vehiculo> {
    return this.request(
      this.http.put<Vehiculo>(`${this.apiUrl}/${id}`, data)
    );
  }

  delete(id: number): Observable<void> {
    return this.request(this.http.delete<void>(`${this.apiUrl}/${id}`));
  }

  private request<T>(request: Observable<T>): Observable<T> {
    this.loading.set(true);
    this.error.set(null);

    return request.pipe(
      catchError((error: unknown) => {
        this.error.set(this.getErrorMessage(error));
        return throwError(() => error);
      }),
      finalize(() => this.loading.set(false))
    );
  }

  private getErrorMessage(error: unknown): string {
    if (error instanceof Error) {
      return error.message;
    }

    return 'No se pudo completar la solicitud.';
  }
}

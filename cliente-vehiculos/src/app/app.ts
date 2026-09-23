import { HttpClient } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  private readonly formBuilder = inject(FormBuilder);
  private readonly http = inject(HttpClient);

  protected readonly isSubmitting = signal(false);
  protected readonly submitMessage = signal('');
  protected readonly submitError = signal(false);

  protected readonly registrationForm = this.formBuilder.nonNullable.group({
    nombre: ['', Validators.required],
    apellido: ['', Validators.required],
    telefono: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    direccion: ['', Validators.required],
    modeloVehiculo: ['', Validators.required],
  });

  protected submit(): void {
    if (this.registrationForm.invalid) {
      this.registrationForm.markAllAsTouched();
      this.submitMessage.set('Completa los campos obligatorios para continuar.');
      this.submitError.set(true);
      return;
    }

    this.isSubmitting.set(true);
    this.submitMessage.set('');
    this.submitError.set(false);

    this.http.post('http://localhost:8087/api/usuarios', this.registrationForm.getRawValue())
      .subscribe({
        next: () => {
          this.isSubmitting.set(false);
          this.submitMessage.set('Registro guardado correctamente.');
          this.registrationForm.reset();
        },
        error: () => {
          this.isSubmitting.set(false);
          this.submitError.set(true);
          this.submitMessage.set('No se pudo guardar el registro. Revisa que el servidor esté activo.');
        },
      });
  }
}

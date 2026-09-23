import { Component, inject, input, OnInit, output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CreateVehiculo, Vehiculo } from '../../models/vehiculo.model';

@Component({
  selector: 'app-vehiculo-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './vehiculo-form.component.html',
})
export class VehiculoFormComponent implements OnInit {
  readonly initialData = input<Vehiculo | null>(null);
  readonly formSubmit = output<CreateVehiculo>();

  private readonly formBuilder = inject(FormBuilder);

  readonly vehiculoForm: FormGroup<{
    modelo: FormControl<string>;
    usuarioId: FormControl<number>;
  }> = this.formBuilder.group({
    modelo: this.formBuilder.nonNullable.control('', Validators.required),
    usuarioId: this.formBuilder.nonNullable.control(0, Validators.required),
  });

  ngOnInit(): void {
    const vehicle = this.initialData();

    if (vehicle) {
      this.vehiculoForm.patchValue({
        modelo: vehicle.modelo,
        usuarioId: vehicle.usuarioId,
      });
    }
  }

  onSubmit(): void {
    if (this.vehiculoForm.invalid) {
      this.vehiculoForm.markAllAsTouched();
      return;
    }

    this.formSubmit.emit(this.vehiculoForm.getRawValue());
  }
}

export interface Usuario {
  id: number;
  nombre: string;
  correo: string;
}

export interface Vehiculo {
  id: number;
  modelo: string;
  usuarioId: number;
}

export type CreateVehiculo = Omit<Vehiculo, 'id'>;

package udemy.empleados.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import udemy.empleados.modelo.Empleado;

public interface EmpleadoRepositorio extends JpaRepository<Empleado,Integer> {}
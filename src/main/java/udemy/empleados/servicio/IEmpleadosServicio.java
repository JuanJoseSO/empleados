package udemy.empleados.servicio;

import udemy.empleados.modelo.Empleado;

import java.util.List;

public interface IEmpleadosServicio {
    public List<Empleado> listarEmpleados();

    public Empleado buscarEmpleadoPorId(Integer idEmpleado);

    //No hace falta un metodo para modificar ya que si el id no fuera nulo, modificaría el registro directamente.
    public void guardarEmpleado(Empleado empleado);

    public void eliminarEmpleado(Empleado empleado);
}
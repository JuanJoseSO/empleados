package udemy.empleados.controlador;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import udemy.empleados.modelo.Empleado;
import udemy.empleados.servicio.EmpleadoServicio;

import java.util.List;

@Controller
public class IndexControlador {
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    //Injección de dependencias automático
    @Autowired
    private EmpleadoServicio empleadoServicio;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String iniciar(ModelMap modelo) {
        List<Empleado> empleados = empleadoServicio.listarEmpleados();
        empleados.forEach(empleado -> logger.info(empleado.toString()));
        //Compartimos el modelo con la vista, esto significa que el  jsp ya va a tener acceso a esta información.
        modelo.put("empleados",empleados);
        return "index";
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.GET)
    public String mostrarAgregar(){
        return "agregar";
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public String agregar(@ModelAttribute("formEmpleado")Empleado empleado, HttpServletRequest request){
        logger.info("Empleado a agregar: "+empleado);
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/"; //Redirige a / que es la ruta inicial

    }

    @RequestMapping(value="/editar", method = RequestMethod.GET)
    public String mostrarEditar(@RequestParam int idEmpleado, ModelMap modelo){
        Empleado empleado=empleadoServicio.buscarEmpleadoPorId(idEmpleado);
        logger.info("Empleado a editar: "+empleado);
        modelo.put("empleado",empleado);
        return "editar"; //Muestra editar.jsp
    }

    @RequestMapping(value="/editar",method = RequestMethod.POST)
    public String editar(@ModelAttribute("formEmpleado") Empleado empleado){
        logger.info("Empleado a guardar: "+empleado);
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/"; //Redirige a / que es la ruta inicial
    }

    @RequestMapping(value="/eliminar",method = RequestMethod.GET)
    public String eliminar(@RequestParam int idEmpleado){
        empleadoServicio.eliminarEmpleado(empleadoServicio.buscarEmpleadoPorId(idEmpleado));
        return "redirect:/"; //Redirige a / que es la ruta inicial
    }
}
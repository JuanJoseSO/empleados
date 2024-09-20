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

//Esta clase es un controlador en el modelo MVC de Spring.
@Controller
public class IndexControlador {
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    /* Injección de dependencias automático. Esto significa que no es necesario crear manualmente una instancia de
        EmpleadoServicio; Spring lo hará por ti cuando el controlador sea instanciado. */
    @Autowired
    private EmpleadoServicio empleadoServicio;

    /* @RequestMapping: Asigna esta función al endpoint / cuando se recibe una solicitud GET. Cuando un usuario accede
        a localhost:8080/empleados/, este metodo será ejecutado.
       Este metodo maneja las solicitudes GET a la página principal. Llama al servicio para obtener una lista de
        empleados.*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String iniciar(ModelMap modelo) {
        List<Empleado> empleados = empleadoServicio.listarEmpleados();
        empleados.forEach(empleado -> logger.info(empleado.toString()));
        /* modelo.put("empleados", empleados): Agrega la lista de empleados al modelo. El objeto ModelMap permite
            compartir datos entre el controlador y la vista (JSP). La clave "empleados" estará disponible en el JSP. */
        modelo.put("empleados",empleados);
        /* Devuelve el nombre de la vista JSP a la que se va a redirigir (en este caso, index.jsp en
            /WEB-INF/jsp/index.jsp). */
        return "index";
    }

    //@RequestMapping: Asocia esta función al endpoint /agregar para las solicitudes GET.
    @RequestMapping(value = "/agregar", method = RequestMethod.GET)
    public String mostrarAgregar(){
        //mostrarAgregar: Simplemente devuelve la vista agregar.jsp para que el usuario pueda agregar un nuevo empleado.
        return "agregar";
    }

    /* @RequestMapping: Asocia esta función al endpoint /agregar pero para las solicitudes POST (es decir,
        cuando se envían datos del formulario).
       @ModelAttribute("formEmpleado"): Spring usa esto para vincular automáticamente los campos del formulario HTML
        con las propiedades del objeto Empleado. El formulario debe tener los campos con los nombres que coincidan
        con las propiedades del objeto.*/
    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public String agregar(@ModelAttribute("formEmpleado")Empleado empleado){
        logger.info("Empleado a agregar: "+empleado);
        empleadoServicio.guardarEmpleado(empleado);
        /* return "redirect:/": Redirige al usuario de vuelta a la página principal (/), lo que recargará la
        lista de empleados con el nuevo empleado agregado. */
        return "redirect:/";
    }

    /* @RequestMapping: Asocia esta función al endpoint /editar con solicitudes GET. En la URL se debe pasar
    el parámetro idEmpleado (por ejemplo, /editar?idEmpleado=1). (se hace en index.jsp en el botón editar)*/
    @RequestMapping(value="/editar", method = RequestMethod.GET)
    /* @RequestParam int idEmpleado: Recupera el parámetro idEmpleado de la solicitud HTTP. Este parámetro
        identifica al empleado que se va a editar. */
    public String mostrarEditar(@RequestParam int idEmpleado, ModelMap modelo){
        Empleado empleado=empleadoServicio.buscarEmpleadoPorId(idEmpleado);
        logger.info("Empleado a editar: "+empleado);
        /* modelo.put("empleado", empleado): Coloca el empleado en el modelo para que los datos estén disponibles
            en la vista editar.jsp. */
        modelo.put("empleado",empleado);
        /* Devuelve el nombre de la vista JSP a la que se va a redirigir (en este caso, editar.jsp. en
            /WEB-INF/jsp/editar.jsp.). */
        return "editar"; //Muestra editar.jsp
    }

    //@RequestMapping: Asocia esta función al endpoint /editar para solicitudes POST.
    @RequestMapping(value="/editar",method = RequestMethod.POST)
    public String editar(@ModelAttribute("formEmpleado") Empleado empleado){
        logger.info("Empleado a guardar: "+empleado);
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/"; //Redirige a / que es la ruta inicial
    }

    //@RequestMapping: Asocia esta función al endpoint /eliminar con solicitudes GET.
    @RequestMapping(value="/eliminar",method = RequestMethod.GET)
    public String eliminar(@RequestParam int idEmpleado){
        empleadoServicio.eliminarEmpleado(empleadoServicio.buscarEmpleadoPorId(idEmpleado));
        return "redirect:/"; //Redirige a / que es la ruta inicial
    }

    /* *ModelMap: es una clase en Spring que se usa para enviar datos desde el controlador hacia la vista (en este caso,
        una página JSP). Sirve para almacenar pares clave-valor, donde las claves son los nombres de los atributos que
        vas a utilizar en la vista, y los valores son los datos que deseas compartir.*/

    /*  *GET se usa para solicitar y mostrar información, mientras que *POST se usa para enviar datos al servidor y
        realizar acciones que modifiquen el estado del servidor, como agregar o actualizar empleados.*/
}
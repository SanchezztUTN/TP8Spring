
package com.example.isi.deso.tp_7;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping(value = {"/{path:[^\\.]*}", "/{path:^(?!api).*}/**"}) // Excluye las rutas de la API
    public String forwardToReact() {
        // Retorna el archivo index.html para que React maneje la navegación
        return "forward:/index.html";
    }
}

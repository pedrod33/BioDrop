package pt.deliveries.business_iniciative.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World !";
    }
}

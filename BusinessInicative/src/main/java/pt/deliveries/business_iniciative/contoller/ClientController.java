package pt.deliveries.business_iniciative.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/businesses-api")
public class ClientController {

    @GetMapping("/")
    public String home() {
        return "Hello Docker World !";
    }

    @PostMapping("/register")
    public String register() {
        return "Hello Docker World !";
    }
}

package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MonitoringController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}


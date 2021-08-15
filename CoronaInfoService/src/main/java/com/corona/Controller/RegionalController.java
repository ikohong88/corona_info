package com.corona.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegionalController {
    @GetMapping("/regional")
    public String getRegional() {
        return "/regional/regional";
    }    
}

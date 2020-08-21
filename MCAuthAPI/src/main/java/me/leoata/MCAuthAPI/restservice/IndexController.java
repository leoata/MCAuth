package me.leoata.MCAuthAPI.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController implements IController {

    @GetMapping("/")
    public String index(){
        return "Index page for MCAuth API";
    }
}

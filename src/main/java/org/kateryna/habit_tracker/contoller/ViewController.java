package org.kateryna.habit_tracker.contoller;

import org.hibernate.Remove;
import org.hibernate.annotations.ConcreteProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @GetMapping("")
    public String redirectToLogin(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}

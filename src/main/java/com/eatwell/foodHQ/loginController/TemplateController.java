package com.eatwell.foodHQ.loginController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/")
public class TemplateController {
    @GetMapping("login")
    public String getLoginView () {
        //Return "Login" with same name as html file in templates: Login
        //Login html file is being returned
        return "Login";
    }

    //redirect after successfuk login
    @GetMapping("foodMenu")
    public String getMenu () {
        //menu html file is being returned
        return "FoodMenu";
    }

}

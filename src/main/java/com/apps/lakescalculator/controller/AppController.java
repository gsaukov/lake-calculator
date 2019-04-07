package com.apps.lakescalculator.controller;

import com.apps.lakescalculator.core.Lake;
import com.apps.lakescalculator.services.LakeCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {

    private InputParser parser;
    private LakeCalculatorService service;

    @GetMapping({"/"})
    public String home(Model model) {
        return "lakecalculator";
    }

    @PostMapping("/")
    public String calculator(@RequestParam("surface") String surface, Model model) {
        //ToDo или правильный error handling или никакого
        try {// cool error handling.
            List<Lake> lakes = service.calculate(parser.parse(surface));
            model.addAttribute("lakes", lakes);
        } catch (Exception e) {
            //ToDo где логирование ошибок?
            return "lakecalculator";
        }
        return "lakecalculator";
    }

    @GetMapping("/{id}")
    public String visualizator(@PathVariable("id") String id, Model model) {
        //ToDo или правильный error handling или никакого
        try {// cool error handling.
            model.addAttribute("visualization", service.visualize(id));
        } catch (Exception e) {
            //ToDo где логирование ошибок?
            return "lakevisualization";
        }
        return "lakevisualization";
    }

    // Todo лучше final переменная и Autowired в конструкторе
    @Autowired
    public void setParser(InputParser parser) {
        this.parser = parser;
    }

    // Todo лучше final переменная и Autowired в конструкторе
    @Autowired
    public void setService(LakeCalculatorService service) {
        this.service = service;
    }
}

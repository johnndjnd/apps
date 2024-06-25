package org.example.jiaoji.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.example.jiaoji.pojo.Class;
import org.example.jiaoji.pojo.RetType;
import org.example.jiaoji.service.ClassService;

import java.util.List;

@RestController
@CrossOrigin
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping("/class")
    public List<Class> getMethodName() {
        return classService.selectAll();
    }

    @PostMapping("/class/addFavor")
    public RetType getMethodName2(@RequestParam Integer classId, @RequestParam Integer userId) {
        return classService.addFavor(classId, userId);
    }

    @PostMapping("/class/deleteFavor")
    public RetType getMethodName3(@RequestParam Integer classId, @RequestParam Integer userId) {
        return classService.deleteFavor(classId, userId);
    }

    @GetMapping("/class/favor")
    public List<Integer> getMethodName4(@RequestParam Integer userId) {
        return classService.selectFavor(userId);
    }
    
}

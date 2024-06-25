package org.example.jiaoji.controller;

import org.example.jiaoji.pojo.Objects;
import org.example.jiaoji.pojo.RetType;
import org.example.jiaoji.pojo.top3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import org.example.jiaoji.service.ObjectService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ObjectController {
    @Autowired
    private ObjectService objectService;

    @CrossOrigin    //解决跨域问题
    @GetMapping("/object/{id}")
    @ResponseBody
    public ResponseEntity<List<Objects>> getObject(@PathVariable("id") Integer id) {
        List<Objects> objects;
        objects = objectService.SelectAllInTopic(id);
        for (Objects object : objects) {
            object.setAveScore(objectService.getAveScore(object.getId()));
            object.setHottestRemark(objectService.getHottestRemark(object.getId()));
        }
        return ResponseEntity.ok(objects);
    }

    @CrossOrigin
    @PostMapping("/object")
    public Integer insert(@RequestBody Objects object) {
        return objectService.InsertObject(object);
    }

    @CrossOrigin    //解决跨域问题
    @GetMapping("/object/remark/{id}")
    @ResponseBody
    public ResponseEntity<List<Objects>> getObjectById(@PathVariable("id") Integer id) {
        List<Objects> objects;
        objects = objectService.SelectById(id);
        for (Objects object : objects) {
            object.setAveScore(objectService.getAveScore(object.getId()));
            object.setHottestRemark(objectService.getHottestRemark(object.getId()));
        }
        return ResponseEntity.ok(objects);
    }

    @CrossOrigin 
    @GetMapping("/object/search/{keyword}")
    @ResponseBody
    public List<Objects> getObjectsByTopicId(@PathVariable("keyword") String keyword) {
        return objectService.search(keyword);
    }

    @CrossOrigin
    @GetMapping("/object/top3/{topicId}")
    @ResponseBody
    public List<top3Object> getObjectsByTopicId(@PathVariable("topicId") Integer topicId) {
        return objectService.SelectTop3(topicId);
    }

    @CrossOrigin
    @DeleteMapping("/object/{id}")
    @ResponseBody
    public RetType delete(@PathVariable("id") Integer id) {
        return objectService.deleteObject(id);
    }
}
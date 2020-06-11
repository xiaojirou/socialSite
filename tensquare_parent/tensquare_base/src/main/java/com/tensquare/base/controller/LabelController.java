package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {

    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result findAll() {
        List<Label> list = labelService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Label label = labelService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", label);
    }

    @PostMapping
    public Result add(@RequestBody Label label) {
        labelService.add(label);
        return new Result(true, StatusCode.OK, "添加成功", null);
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody Label label, @PathVariable String id) {
        label.setId(id);
        labelService.updateById(label);
        return new Result(true, StatusCode.OK, "修改成功", null);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功", null);
    }

    @GetMapping("/toplist")
    public Result getTopList() {
        List<Label> list = labelService.getTopList();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @PostMapping("/search/{page}/{size}")
    public Result search(@RequestBody(required = false) Map map, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Label> page1 = labelService.search(map, pageable);
        PageResult<Label> pageResult = new PageResult<>(page1.getTotalElements(), page1.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

}

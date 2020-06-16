package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {
    @Autowired
    private SpitService spitService;

    /**
     * 发表吐槽
     * @param spit
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spit spit) {
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "吐槽成功");
    }

    /**
     * 修改吐槽
     * @param spit
     * @return
     */
    @PutMapping("/{spitId}")
    public Result update(@PathVariable String spitId, @RequestBody Spit spit) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除吐槽
     * @param spitId
     * @return
     */
    @DeleteMapping("/{spitId}")
    public Result deleteById(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据id获取吐槽
     * @param spitId
     * @return
     */
    @GetMapping("/{spitId}")
    public Result findById(@PathVariable String spitId) {
        Spit spit = spitService.findById(spitId);
        return new Result(true, StatusCode.OK, "获取成功", spit);
    }

    /**
     * 根据上级吐槽id查询评论数据
     * @param spitId
     * @return
     */
    @GetMapping("/comment/{spitId}/{page}/{size}")
    public Result findByParentId(@PathVariable String spitId, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Spit> spitPage = spitService.findByParentId(spitId, pageable);
        PageResult<Spit> pageResult = new PageResult<>(spitPage.getTotalElements(), spitPage.getContent());
        return new Result(true, StatusCode.OK, "获取成功", pageResult);
    }

    /**
     * 为吐槽点赞
     * @param spitId
     * @return
     */
    @PutMapping("/thumbup/{spitId}")
    public Result updateThumbUp(@PathVariable String spitId) {
        spitService.updateThumbUp(spitId);
        return new Result(true, StatusCode.OK, "点赞成功");
    }
}

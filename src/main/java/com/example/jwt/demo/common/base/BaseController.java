package com.example.jwt.demo.common.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.jwt.demo.common.util.ResultUtils;
import com.example.jwt.demo.common.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 谢霜
 * @Date: 2018/09/12 下午 16:22
 * @Description:
 */
public class BaseController<E extends Serializable> {
    /**
     * 获取service
     * @return
     */
    @Autowired
    protected IService<E> iService;

    @GetMapping(value = "{id}")
    @ResponseBody
    @ApiOperation(value = "通过id获取")
    public Result<E> get(@PathVariable(value = "id") String id){
        E entity = iService.getById(id);
        return new ResultUtils<E>().setData(entity);
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "分页获取数据")
    public Result getAll(@RequestParam(value = "page",defaultValue = "1") Integer page,
                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        Page<E> ipage = new Page<>(page,size);
        LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();
        IPage<E> page1 = iService.page(ipage,null);
        return new ResultUtils().setData(page1);
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "添加数据")
    public Result add(E e){
        if (iService.save(e)){
            return new ResultUtils().setSuccessMsg("添加成功");
        }
        return new ResultUtils().setErrorMsg("添加失败");
    }

    @PutMapping
    @ResponseBody
    @ApiOperation(value = "修改数据")
    public Result put(E e){
        if (iService.updateById(e)){
            return new ResultUtils().setSuccessMsg("修改成功");
        }
        return new ResultUtils().setErrorMsg("修改失败");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "删除数据")
    public Result del(@PathVariable(value = "id") String id){
        List<Integer> list = Arrays.asList(id.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        try{
            if (iService.removeByIds(list)){
                return new ResultUtils().setSuccessMsg("删除成功");
            } else {
                return new ResultUtils().setSuccessMsg("删除失败");
            }
        } catch (Exception e) {
            return new ResultUtils().setSuccessMsg("删除失败");
        }
    }
}

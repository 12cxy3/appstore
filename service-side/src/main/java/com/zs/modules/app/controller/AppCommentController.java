package com.zs.modules.app.controller;

import com.zs.common.annotation.LogOperation;
import com.zs.common.constant.Constant;
import com.zs.common.page.PageData;
import com.zs.common.utils.ExcelUtils;
import com.zs.common.utils.Result;
import com.zs.common.validator.AssertUtils;
import com.zs.common.validator.ValidatorUtils;
import com.zs.common.validator.group.AddGroup;
import com.zs.common.validator.group.DefaultGroup;
import com.zs.common.validator.group.UpdateGroup;
import com.zs.modules.app.dto.AppCommentDTO;
import com.zs.modules.app.excel.AppCommentExcel;
import com.zs.modules.app.service.AppCommentService;
import com.zs.modules.security.user.SecurityUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * app评论
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-10
 */
@RestController
@RequestMapping("app/appcomment")
@Api(tags="app评论")
public class AppCommentController {
    @Autowired
    private AppCommentService appCommentService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    public Result<PageData<AppCommentDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<AppCommentDTO> page = appCommentService.getPage(params);

        return new Result<PageData<AppCommentDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public Result<AppCommentDTO> get(@PathVariable("id") Long id){
        AppCommentDTO data = appCommentService.get(id);

        return new Result<AppCommentDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    public Result save(@RequestBody AppCommentDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        dto.setCreateDate(new Date());
        dto.setCreator(SecurityUser.getUserId());
        appCommentService.save(dto);
        return new Result();
    }

    @GetMapping("commentCount")
    @ApiOperation("查询是否评价过该款App")
    @LogOperation("保存")
    public Result commitCount(@RequestParam Long appId){
        //效验数据
        int count = appCommentService.commitCount(appId,SecurityUser.getUserId());
        return new Result().ok(count);
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    public Result update(@RequestBody AppCommentDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        appCommentService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        appCommentService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<AppCommentDTO> list = appCommentService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "app评论", list, AppCommentExcel.class);
    }

}
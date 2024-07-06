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
import com.zs.modules.app.dto.AppInfoDTO;
import com.zs.modules.app.excel.AppInfoExcel;
import com.zs.modules.app.service.AppInfoService;
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
 * App管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-09
 */
@RestController
@RequestMapping("app/appinfo")
@Api(tags="App管理")
public class AppInfoController {
    @Autowired
    private AppInfoService appInfoService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    public Result<PageData<AppInfoDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<AppInfoDTO> page = appInfoService.getPage(params);

        return new Result<PageData<AppInfoDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public Result<AppInfoDTO> get(@PathVariable("id") Long id){
        AppInfoDTO data = appInfoService.get(id);

        return new Result<AppInfoDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    public Result save(@RequestBody AppInfoDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        dto.setCreateDate(new Date());
        dto.setCreator(SecurityUser.getUserId());
        appInfoService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
//    @RequiresPermissions("app:appinfo:update")
    public Result update(@RequestBody AppInfoDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        dto.setStatus(0);
        appInfoService.update(dto);

        return new Result();
    }

    @PutMapping("audit")
    @ApiOperation("审核")
    @LogOperation("审核")
    @RequiresPermissions("app:appinfo:audit")
    public Result audit(@RequestBody AppInfoDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        appInfoService.audit(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        appInfoService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("app:appinfo:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<AppInfoDTO> list = appInfoService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "App管理", list, AppInfoExcel.class);
    }

}
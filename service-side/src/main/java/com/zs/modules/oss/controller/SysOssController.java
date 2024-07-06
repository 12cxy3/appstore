

package com.zs.modules.oss.controller;

import cn.hutool.core.io.file.FileNameUtil;
import com.zs.common.exception.ErrorCode;
import com.zs.common.utils.Result;
import com.zs.modules.oss.cloud.CloudStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("sys/oss")
@Api(tags = "文件上传")
@AllArgsConstructor
public class SysOssController {

    private final CloudStorageService cloudStorageService;

    @PostMapping("upload")
    @ApiOperation(value = "上传文件")
    public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }

        //上传文件
        String suffix = FileNameUtil.getSuffix(file.getOriginalFilename());
        String url = cloudStorageService.upload(file.getBytes(),file.getOriginalFilename());

        Map<String, Object> data = new HashMap<>(1);
        data.put("src", url);

        return new Result<Map<String, Object>>().ok(data);
    }

}
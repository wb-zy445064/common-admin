package com.xieke.admin.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xieke.admin.dto.ResultInfo;
import com.xieke.admin.entity.Log;
import com.xieke.admin.service.ILogService;
import com.xieke.admin.util.FormatUtil;
import com.xieke.admin.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author Auto Generator
 * @since 2018-10-27
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {

    @Resource
    private ILogService ilogService;

    @RequestMapping("/*")
    public void toHtml(){

    }

    @RequestMapping("/listData")
    @RequiresPermissions("log:view")
    public @ResponseBody
        ResultInfo<List<Log>> listData(String userName, String operTime, Integer page, Integer limit){
        Log log = new Log();
        log.setUserName(userName);
        QueryWrapper<Log> wrapper = new QueryWrapper<>(log);
        if(!StringUtils.isEmpty(operTime)){
            wrapper.ge("create_time",FormatUtil.parseDate(operTime.split(" - ")[0]+" 00:00:00", null));
            wrapper.le("create_time",FormatUtil.parseDate(operTime.split(" - ")[1]+" 23:59:59", null));
        }
        wrapper.orderBy(true,false,"create_time");
        IPage<Log> pageObj = ilogService.page(new Page<>(page,limit), wrapper);
        return new ResultInfo<>(pageObj.getRecords(), (int)pageObj.getTotal());
    }

}

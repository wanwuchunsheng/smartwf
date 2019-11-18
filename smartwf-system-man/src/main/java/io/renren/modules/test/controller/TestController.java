package io.renren.modules.test.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.test.service.ITestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/test/user")
@Api("TEST CURD测试接口")
public class TestController extends AbstractController{
	
	@Autowired
	ITestService testService;
	
	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	//@RequiresPermissions("test:user:list")
	@ApiOperation(value="查询所有用户接口", notes="查询所有注册用户")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = testService.queryPage(params);
		return R.ok().put("page", page);
	}

}

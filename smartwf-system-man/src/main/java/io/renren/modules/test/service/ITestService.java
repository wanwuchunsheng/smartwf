package io.renren.modules.test.service;

import java.util.Map;

import io.renren.common.utils.PageUtils;

public interface ITestService {

	PageUtils queryPage(Map<String, Object> params);

}

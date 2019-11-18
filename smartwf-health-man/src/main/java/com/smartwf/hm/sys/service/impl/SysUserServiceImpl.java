package com.smartwf.hm.sys.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.config.rocketmq.Producer;
import com.smartwf.hm.sys.dao.SysUserDao;
import com.smartwf.hm.sys.pojo.SysUser;
import com.smartwf.hm.sys.service.SysUserService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @Description: 系统业务层实现
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private Producer producer;
	
	/**
     * 分页查询用户
     *
     * @param page
     * @return
     */
	@Override
	public Result selectSysUserByPage(Page page, SysUser sysUser) {
		com.github.pagehelper.Page<Object> objectPage = PageHelper.startPage(page.getPage(), page.getLimit());
		Example example = new Example(SysUser.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(sysUser.getName())) {
            criteria.andLike("name", Constants.PER_CENT + sysUser.getName() + Constants.PER_CENT);
        }
        if (!StringUtils.isEmpty(sysUser.getTel())) {
            criteria.andLike("tel", Constants.PER_CENT + sysUser.getTel() + Constants.PER_CENT);
        }
        if (!StringUtils.isEmpty(sysUser.getAddress())) {
            criteria.andLike("address", Constants.PER_CENT + sysUser.getAddress() + Constants.PER_CENT);
        }
		List<SysUser> list=this.sysUserDao.selectByExample(example);
		try {
			producer.send("PTP_TEST01", "BBBB", "[\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a8504\"\n" + 
					"    },\n" + 
					"    \"name\": \"GW_2000\",\n" + 
					"    \"WTUR_Wtid\": \"4\",\n" + 
					"    \"min\": 1524717838,\n" + 
					"    \"max\": 1538063998\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a8505\"\n" + 
					"    },\n" + 
					"    \"name\": \"GW_2000\",\n" + 
					"    \"WTUR_Wtid\": \"1\",\n" + 
					"    \"min\": 1524664357,\n" + 
					"    \"max\": 1538063999\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a8506\"\n" + 
					"    },\n" + 
					"    \"name\": \"GW_2000\",\n" + 
					"    \"WTUR_Wtid\": \"2\",\n" + 
					"    \"min\": 1524664064,\n" + 
					"    \"max\": 1538063998\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a8507\"\n" + 
					"    },\n" + 
					"    \"name\": \"GW_2000\",\n" + 
					"    \"WTUR_Wtid\": \"50\",\n" + 
					"    \"min\": 1524368090,\n" + 
					"    \"max\": 1538063999\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a8508\"\n" + 
					"    },\n" + 
					"    \"name\": \"GW_2000\",\n" + 
					"    \"WTUR_Wtid\": \"3\",\n" + 
					"    \"min\": 1524717483,\n" + 
					"    \"max\": 1538063999\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a8509\"\n" + 
					"    },\n" + 
					"    \"name\": \"GW_2000\",\n" + 
					"    \"WTUR_Wtid\": \"5\",\n" + 
					"    \"min\": 1524718459,\n" + 
					"    \"max\": 1538063999\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a850a\"\n" + 
					"    },\n" + 
					"    \"name\": \"GW_2000\",\n" + 
					"    \"WTUR_Wtid\": \"47\",\n" + 
					"    \"min\": 1526050840,\n" + 
					"    \"max\": 1538063999\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a850b\"\n" + 
					"    },\n" + 
					"    \"name\": \"GW_2000\",\n" + 
					"    \"WTUR_Wtid\": \"37\",\n" + 
					"    \"min\": 1524575546,\n" + 
					"    \"max\": 1538063998\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a850c\"\n" + 
					"    },\n" + 
					"    \"name\": \"GW_2000\",\n" + 
					"    \"WTUR_Wtid\": \"10\",\n" + 
					"    \"min\": 1524573985,\n" + 
					"    \"max\": 1538063999\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a850d\"\n" + 
					"    },\n" + 
					"    \"name\": \"WT_2000\",\n" + 
					"    \"WTUR_Wtid\": \"4\",\n" + 
					"    \"min\": 1514736000,\n" + 
					"    \"max\": 1535623934\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a850e\"\n" + 
					"    },\n" + 
					"    \"name\": \"WT_2000\",\n" + 
					"    \"WTUR_Wtid\": \"8\",\n" + 
					"    \"min\": 1514736000,\n" + 
					"    \"max\": 1535623934\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a850f\"\n" + 
					"    },\n" + 
					"    \"name\": \"WT_2000\",\n" + 
					"    \"WTUR_Wtid\": \"17\",\n" + 
					"    \"min\": 1514736000,\n" + 
					"    \"max\": 1535710334\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a8510\"\n" + 
					"    },\n" + 
					"    \"name\": \"WT_2000\",\n" + 
					"    \"WTUR_Wtid\": \"18\",\n" + 
					"    \"min\": 1514736000,\n" + 
					"    \"max\": 1535710334\n" + 
					"  },\n" + 
					"  {\n" + 
					"    \"_id\": {\n" + 
					"      \"$oid\": \"5da19904d3b9c1365c0a8511\"\n" + 
					"    },\n" + 
					"    \"name\": \"WT_2000\",\n" + 
					"    \"WTUR_Wtid\": \"16\",\n" + 
					"    \"min\": 1514736000,\n" + 
					"    \"max\": 1535710334\n" + 
					"  }\n" + 
					"]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Result.data(objectPage.getTotal(), list,0,"");
	}

	/**
     * 添加用户
     *
     * @return
     */
	@Transactional //事务
	@Override
	public void saveSysUser(SysUser sysUser) {
		this.sysUserDao.insertSelective(sysUser);
		log.info("插入成功，返回值:{}",sysUser.getId());
	}

	/**
     * 登录查询
     *
     * @return
     */
	@Override
	public Result queryUserByParam(SysUser user) {
		SysUser userInfo= this.sysUserDao.selectOne(user);
		return Result.data(userInfo);
	}

	/**
     * 修改用户
     *
     */
	@Override
	public void updateSysUser(SysUser sysUser) {
		this.sysUserDao.updateByPrimaryKeySelective(sysUser);
	}

	/**
     * 删除用户
     *
     */
	@Override
	public void deleteSysUser(SysUser sysUser) {
		this.sysUserDao.deleteByPrimaryKey(sysUser);
	}

	/**
     * 主键查询用户
     *
     */
	@Override
	public Result<?> selectSysUserById(SysUser sysUser) {
		SysUser userInfo= this.sysUserDao.selectByPrimaryKey(sysUser);
		return Result.data(userInfo);
	}
}

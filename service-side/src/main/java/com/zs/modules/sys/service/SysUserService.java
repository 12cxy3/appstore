package com.zs.modules.sys.service;

import com.zs.common.page.PageData;
import com.zs.common.service.BaseService;
import com.zs.modules.sys.dto.SysUserDTO;
import com.zs.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author Mark sunlightcs@gmail.com
 */
public interface SysUserService extends BaseService<SysUserEntity> {

	PageData<SysUserDTO> page(Map<String, Object> params);

	List<SysUserDTO> list(Map<String, Object> params);

	SysUserDTO get(Long id);

	SysUserDTO getByUsername(String username);

	void save(SysUserDTO dto);

	void update(SysUserDTO dto);

	void delete(Long[] ids);

	/**
	 * 修改密码
	 * @param id           用户ID
	 * @param newPassword  新密码
	 */
	void updatePassword(Long id, String newPassword);


}

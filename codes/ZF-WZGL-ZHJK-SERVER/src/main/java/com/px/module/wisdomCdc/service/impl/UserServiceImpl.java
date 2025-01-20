package com.px.module.wisdomCdc.service.impl;

import com.px.module.wisdomCdc.dao.*;
import com.px.module.wisdomCdc.dto.UserDto;
import com.px.module.wisdomCdc.entity.*;
import com.px.module.common.base.BaseService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.exception.RequestException;
import com.px.module.common.utils.MD5EncryptUtil;
import com.px.module.common.utils.TokenUtil;
import com.px.module.common.utils.UUIDUtil;
import com.px.module.wisdomCdc.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 品讯科技
 * @version V 1.0
 * @Package com.px.module.goods.service.impl
 * @ClassName UserServiceImpl
 * @Description UserServiceImpl
 * @time 2019-09-17 10:24:19
 */
@Service
public class UserServiceImpl extends BaseService implements IUserService<UserModel> {
    @Autowired
    public IUserMapper userMapper;
    @Autowired
    public IRoleMapper roleMapper;

    @Autowired
    public IOrgMapper orgMapper;

    @Autowired
    public IDepartmentMapper departmentMapper;

    @Autowired
    public ICommunityMapper communityMapper;

    /*@Autowired
    public ICompanyService companyService;*/

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * @param userModel
     * @return JSONObject
     * @throws
     * @name save
     * @author 品讯科技
     * @Description 保存
     * @time 2019-09-17 10:24:19
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(UserModel userModel) {
        try {
            if (userMapper.findByLoginName(userModel.getLoginName()) != null) {
                return Result.buildResult(ReturnMsgEnum.LoginName_Repeat);
            }
            String id = UUIDUtil.get32UUID();
            String passWord = MD5EncryptUtil.encrypt(userModel.getPassWord () + userModel.getLoginName());
            userModel.setId(UUIDUtil.get32UUID());
            userModel.setPassWord(passWord);
            userModel.setMark ("1");
            if (userMapper.save(userModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            return getResult(ReturnMsgEnum.Save_Success, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
    }

    /**
     * @param ids
     * @return JSONObject
     * @throws
     * @name deleteByIds
     * @author 品讯科技
     * @Description 物理删除
     * @time 2019-09-18 14:11:47
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        try {
            if (userMapper.deleteByIds(ids) < 1) {
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
        return getResult(ReturnMsgEnum.Delete_Success);
    }

    /**
     * @param ids
     * @return JSONObject
     * @throws
     * @name logicDeleteByIds
     * @author 品讯科技
     * @Description 逻辑删除
     * @time 2019-09-18 14:11:47
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        try {
            if (userMapper.logicDeleteByIds(ids) < 1) {
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
        return getResult(ReturnMsgEnum.Delete_Success);
    }


    /**
     * @param userModel
     * @return JSONObject
     * @throws
     * @name update
     * @author 品讯科技
     * @Description 修改
     * @time 2019-09-18 14:11:47
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(UserModel userModel) {
        try {
            if (userMapper.update(userModel) < 1) {
                return getResult(ReturnMsgEnum.Update_Failure);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
        return getResult(ReturnMsgEnum.Update_Success);
    }

    /**
     * @param id
     * @return JSONObject
     * @throws
     * @name findById
     * @author 品讯科技
     * @Description 根据条件查询所有数据
     * @time 2019-09-18 14:11:47
     */
    @Override
    public Result findById(String id) {
        try {
            return getResult(ReturnMsgEnum.OK, userMapper.findById(id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
    }

    /**
     * @param userDto
     * @return JSONObject
     * @throws
     * @name findAll
     * @author 品讯科技
     * @Description 根据条件查询所有数据并分页
     * @time 2019-09-18 14:11:47
     */
    @Override
    public Result findAll(UserDto userDto) {
        try {
            page(userDto);//分页判断
            return getResult(ReturnMsgEnum.OK, userMapper.findAll(userDto));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
    }


    @Override
    public Result findList(UserDto userDto) {
        try {
            page(userDto);//分页判断
            return getResult(ReturnMsgEnum.OK, userMapper.findList(userDto.getUserName (),userDto.getPhone (),userDto.getDeptId (),userDto.getOrgId ()));//此方法有问题
//            return getResult(ReturnMsgEnum.OK, userMapper.findList2(userDto.getUserName (),userDto.getPhone (),userDto.getCompanyCode ()));//这是为了测试
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
    }

    @Override
    public Result findByUserId(String id) {
        try {
            return getResult(ReturnMsgEnum.OK, userMapper.findByUserInfoId(id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
    }

    @Override
    public Result updateStatus(UserModel userModel) {
        try {
            if (userMapper.updateStatus(userModel.getId (),userModel.getStatus ()) < 1) {
                return getResult(ReturnMsgEnum.Update_Failure);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.ERROR();
        }
        return getResult(ReturnMsgEnum.Update_Success);
    }
    @Override
    public Result resetPassword(String id, String loginName, String passWord) {
        loginName = userMapper.findLoginNameByUserId(id);
        String passWordMd5 = MD5EncryptUtil.encrypt(passWord + loginName);
        if (userMapper.resetPassword(id, passWordMd5) < 1) {
            return getResult(ReturnMsgEnum.Update_Failure);
        }
        return getResult(ReturnMsgEnum.OK);
    }

    @Override
    public Result updatePassword(String loginName, String passWord, String oldPassWord) {
        UserModel user = userMapper.findByLoginName (loginName);
        if(!user.getPassWord ().equals(MD5EncryptUtil.encrypt(oldPassWord+loginName))) {
            throw new RequestException(ReturnMsgEnum.Login_Error.getMsg(),ReturnMsgEnum.Login_Error);
        }
        String passWordMd5 = MD5EncryptUtil.encrypt(passWord + loginName);
        if (userMapper.resetPassword(user.getId (), passWordMd5) < 1) {
            return getResult(ReturnMsgEnum.Update_Failure);
        }
        return getResult(ReturnMsgEnum.OK);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result saveUser(UserDto userDto) {
        if (userMapper.findByLoginName(userDto.getLoginName()) != null) {
            throw new RequestException (ReturnMsgEnum.LoginName_Repeat.getMsg(),ReturnMsgEnum.LoginName_Repeat);
        }
        userDto.setMark ("0");//0 系统用户 1 客户
        //1：初始化密码&&用户表信息
        String passWord = MD5EncryptUtil.encrypt("123456" + userDto.getLoginName());
        userDto.setId(UUIDUtil.get32UUID());
        userDto.setPassWord(passWord);
        String id = UUIDUtil.get32UUID();
        userDto.setId(id);
        if (userMapper.save(userDto) < 1) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        //2：插入机构用户关联表
        //获取岗位信息,判断是否存在
        OrgModel org=orgMapper.findById (userDto.getOrgId ());
        if(org==null||"".equals (org.getId ())){
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        if (userMapper.saveUserOrg (UUIDUtil.get32UUID(),userDto.getId (),userDto.getOrgId ()) < 1) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        //3：插入用户部门关联表
        //获取机构信息，判断是否存在
        DepartmentModel dept=departmentMapper.findById (userDto.getDeptId ());
        if(dept==null||"".equals (dept.getId ())){
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        if (userMapper.saveUserDept (UUIDUtil.get32UUID(),userDto.getId (),userDto.getDeptId ()) < 1) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }

        //4：插入用户角色关联表
        RoleModel role=roleMapper.findById (userDto.getRoleId ());
        if(role==null||"".equals (role.getId ())){
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        if (userMapper.saveUserRole (UUIDUtil.get32UUID(),userDto.getId (),userDto.getRoleId ()) < 1) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }

        //5：插入用户社区关联表
        for(int i =0;i<userDto.getCommunityIdList ().size ();i++){
            //获取机构信息，判断是否存在
            CommunityModel community=communityMapper.findById (userDto.getCommunityIdList ().get (0));
            if(community==null||"".equals (community.getId ())){
                throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
            }
            if (userMapper.saveUserCommunity (UUIDUtil.get32UUID(),userDto.getId (),community.getCommunityId ()) < 1) {
                throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
            }
        }

        return getResult(ReturnMsgEnum.Save_Success, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result updateUser(UserDto userDto) {
        //判断是否存在
        if (userMapper.findById (userDto.getId ()) == null) {
            throw new RequestException (ReturnMsgEnum.User_IsNull.getMsg(),ReturnMsgEnum.User_IsNull);
        }
        //1：修改用户
        if (userMapper.update (userDto) < 1) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        //2：删除岗位用户关系表&&新增
        if (userMapper.deleteUserOrg (userDto.getId ()) < 0) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        OrgModel org=orgMapper.findById (userDto.getOrgId ());
        if(org==null||"".equals (org.getId ())){
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        if (userMapper.saveUserOrg (UUIDUtil.get32UUID(),userDto.getId (),userDto.getOrgId ()) < 1) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }

        //3：删除部门用户关系表&&新增
        if (userMapper.deleteUserDept (userDto.getId ()) < 0) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        //获取公司信息，判断是否存在
        DepartmentModel dept=departmentMapper.findById (userDto.getDeptId ());
        if(dept==null||"".equals (dept.getId ())){
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        if (userMapper.saveUserDept (UUIDUtil.get32UUID(),userDto.getId (),userDto.getDeptId ()) < 1) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        //4：删除角色用户关系表&&新增
        if (userMapper.deleteUserRole (userDto.getId ()) < 0) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        RoleModel role=roleMapper.findById (userDto.getRoleId ());
        if(role==null||"".equals (userDto.getId ())){
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        if (userMapper.saveUserRole (UUIDUtil.get32UUID(),userDto.getId (),userDto.getRoleId ()) < 1) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }

        //5：删除社区用户关系表&&新增 插入用户社区关联表
        if (userMapper.deleteUserCommunity (userDto.getId ()) < 0) {
            throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
        }
        for(int i =0;i<userDto.getCommunityIdList ().size ();i++){
            //获取机构信息，判断是否存在
            CommunityModel community=communityMapper.findById (userDto.getCommunityIdList ().get (i));
            if(community==null||"".equals (community.getId ())){
                throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
            }
            if (userMapper.saveUserCommunity (UUIDUtil.get32UUID(),userDto.getId (),community.getCommunityId ()) < 1) {
                throw new RequestException (ReturnMsgEnum.Save_Failed.getMsg(),ReturnMsgEnum.Save_Failed);
            }
        }
        return getResult(ReturnMsgEnum.Save_Success, userDto.getId ());

    }



	@Override
	  @Transactional(propagation = Propagation.REQUIRED)
	public Result finduser(String token) {

	
		try {
            //首先查询这个用户的信息
			String userId = TokenUtil.getUserId(token);
			UserExpandModel findById = userMapper.findByuserIds(userId);
       	
       	
           return getResult(ReturnMsgEnum.OK, userMapper.finduser(findById));
       } catch (Exception e) {
           logger.error(e.getMessage(), e);
           return Result.ERROR();
       }
	}
}

package com.atguigu.atcrowdfunding.mapper;


import java.util.List;

import com.atguigu.atcrowdfunding.pojo.TAdminRole;
import com.atguigu.atcrowdfunding.pojo.TAdminRoleExample;
import org.apache.ibatis.annotations.Param;

public interface TAdminRoleMapper {
    long countByExample(TAdminRoleExample example);

    int deleteByExample(TAdminRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAdminRole record);

    int insertSelective(TAdminRole record);

    List<TAdminRole> selectByExample(TAdminRoleExample example);

    TAdminRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAdminRole record, @Param("example") TAdminRoleExample example);

    int updateByExample(@Param("record") TAdminRole record, @Param("example") TAdminRoleExample example);

    int updateByPrimaryKeySelective(TAdminRole record);

    int updateByPrimaryKey(TAdminRole record);

	List<Integer> listRoleIdByAdminId(Integer id);

	void saveAdminAndRoleRelationship(@Param("adminId") Integer adminId,@Param("roleIds") Integer[] roleIds);

	void deleteAdminAndRoleRelationship(@Param("adminId") Integer adminId,@Param("roleIds") Integer[] roleIds);

    List<Integer> getRoleIdsByAdminId(Integer id);
}
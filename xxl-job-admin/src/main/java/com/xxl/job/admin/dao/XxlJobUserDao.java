package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.beetl.utils.CustomCondition;
import com.xxl.job.admin.beetl.utils.CustomQuery;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;
import org.beetl.sql.core.query.LambdaQuery;

import java.util.List;

/**
 * 用户管理
 *
 * @author Mars
 * @date 2019/10/22
 */
public interface XxlJobUserDao extends BaseMapper<XxlJobUser> {

    /**
     * 分页查询
     * @param pageQuery
     * @param username
     * @param role
     * @return
     */
    default PageQuery<XxlJobUser> pageQuery(PageQuery<XxlJobUser> pageQuery, String username, int role) {
        return createLambdaQuery().andLike(XxlJobUser::getUsername, CustomQuery.filterLikeEmpty(username))
                .andEq(XxlJobUser::getRole, CustomQuery.conditionNumber(role, CustomCondition.GT, -1))
                .page(pageQuery.getPageNumber(),pageQuery.getPageSize());
    }

    /**
     * 根据用户名读取用户信息
     * @param username
     * @return
     */
    default XxlJobUser loadByUserName(String username){
        LambdaQuery<XxlJobUser> lambdaQuery = createLambdaQuery();
        XxlJobUser xxlJobUser = lambdaQuery.andEq(XxlJobUser::getUsername, username).single();
        return xxlJobUser;
    }

    default int update(XxlJobUser xxlJobUser) {
        return createLambdaQuery().andEq(XxlJobUser::getId, xxlJobUser.getId()).updateSelective(xxlJobUser);
    }
}

/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserTest.java 
 *
 * Created: [2014-12-12 下午7:10:59] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.user;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.dao.IUserSecurityDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.helper.PKGenarator;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UserTest {
    @Resource
    private IUserDao userDao;
    @Resource
    private IUserSecurityDao userSecurityDao;

    @Test
    public void selectUser() {
        UserModel user = userDao.selectOneByField("selectByUserId", "aaaaaaaa");
        System.out.println(user);

    }

    @Test
    public void saveUser() {
        UserModel user = new UserModel();
        user.setUserId("test222");
        user.setPassword("22222");
        user.setId(PKGenarator.getId());
        userDao.save(user);

    }

    @Test
    public void selectUserSecurity() {
        UserSecurityModel model = this.userSecurityDao.findByUserId(
                "wangfei11", "P");
        System.out.println(model.getUserId());
    }
}

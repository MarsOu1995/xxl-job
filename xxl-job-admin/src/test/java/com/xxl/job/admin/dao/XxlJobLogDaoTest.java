package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.core.model.result.TriggerCountByDay;
import com.xxl.job.admin.beetl.utils.RowPageQuery;
import org.beetl.sql.core.engine.PageQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XxlJobLogDaoTest {

    @Resource
    private XxlJobLogDao xxlJobLogDao;

    @Test
    public void test(){
        PageQuery<XxlJobLog> xxlJobLogPageQuery = xxlJobLogDao.pageList(new RowPageQuery(0,10), 1, 1, null, null, 1);
        List<XxlJobLog> list = xxlJobLogPageQuery.getList();
        long list_count = xxlJobLogPageQuery.getTotalRow();

        XxlJobLog log = new XxlJobLog();
        log.setJobGroup(1L);
        log.setJobId(1L);

        long ret1 = xxlJobLogDao.createLambdaQuery().insert(log);
        XxlJobLog dto = xxlJobLogDao.single(log.getId());

        log.setTriggerTime(new Date());
        log.setTriggerCode(1);
        log.setTriggerMsg("1");
        log.setExecutorAddress("1");
        log.setExecutorHandler("1");
        log.setExecutorParam("1");
        ret1 = xxlJobLogDao.updateTriggerInfo(log);
        dto = xxlJobLogDao.single(log.getId());


        log.setHandleTime(new Date());
        log.setHandleCode(2);
        log.setHandleMsg("2");
        ret1 = xxlJobLogDao.updateHandleInfo(log);
        dto = xxlJobLogDao.single(log.getId());


        List<TriggerCountByDay> list2 = xxlJobLogDao.triggerCountByDay(new Date(new Date().getTime() + 30*24*60*60*1000), new Date());

        int ret4 = xxlJobLogDao.clearLog(1, 1, new Date(), 100);

        int ret2 = xxlJobLogDao.deleteById(log.getJobId());

        long ret3 = xxlJobLogDao.triggerCountByHandleCode(-1);
    }

}

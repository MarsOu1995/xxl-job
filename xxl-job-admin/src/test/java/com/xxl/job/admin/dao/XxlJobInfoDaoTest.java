package com.xxl.job.admin.dao;

import com.xxl.job.admin.beetl.utils.RowPageQuery;
import com.xxl.job.admin.core.model.XxlJobInfo;
import org.beetl.sql.core.engine.PageQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XxlJobInfoDaoTest {
	
	@Resource
	private XxlJobInfoDao xxlJobInfoDao;
	
	@Test
	public void pageList(){
		PageQuery<XxlJobInfo> xxlJobInfoPageQuery = xxlJobInfoDao.pageQuery(new RowPageQuery<>(0, 20), 0, -1, null, null, null);
		long list_count = xxlJobInfoPageQuery.getTotalRow();
		List<XxlJobInfo> list = xxlJobInfoPageQuery.getList();

		System.out.println(list);
		System.out.println(list_count);

		List<XxlJobInfo> list2 = xxlJobInfoDao.getJobsByGroup(1);
	}
	
	@Test
	public void save_load(){
		XxlJobInfo info = new XxlJobInfo();
		info.setJobGroup(1L);
		info.setJobCron("jobCron");
		info.setJobDesc("desc");
		info.setAuthor("setAuthor");
		info.setAlarmEmail("setAlarmEmail");
		info.setExecutorRouteStrategy("setExecutorRouteStrategy");
		info.setExecutorHandler("setExecutorHandler");
		info.setExecutorParam("setExecutorParam");
		info.setExecutorBlockStrategy("setExecutorBlockStrategy");
		info.setGlueType("setGlueType");
		info.setGlueSource("setGlueSource");
		info.setGlueRemark("setGlueRemark");
		info.setChildJobId("1");

		int count = xxlJobInfoDao.createLambdaQuery().insert(info);

		XxlJobInfo info2 = xxlJobInfoDao.single(info.getId());
		info2.setJobCron("jobCron2");
		info2.setJobDesc("desc2");
		info2.setAuthor("setAuthor2");
		info2.setAlarmEmail("setAlarmEmail2");
		info2.setExecutorRouteStrategy("setExecutorRouteStrategy2");
		info2.setExecutorHandler("setExecutorHandler2");
		info2.setExecutorParam("setExecutorParam2");
		info2.setExecutorBlockStrategy("setExecutorBlockStrategy2");
		info2.setGlueType("setGlueType2");
		info2.setGlueSource("setGlueSource2");
		info2.setGlueRemark("setGlueRemark2");
		info2.setGlueUpdatetime(new Date());
		info2.setChildJobId("1");

		int item2 = xxlJobInfoDao.updateById(info2);

		xxlJobInfoDao.deleteById(info2.getId());

		List<XxlJobInfo> list2 = xxlJobInfoDao.getJobsByGroup(1);

		long ret3 = xxlJobInfoDao.allCount();

	}

}

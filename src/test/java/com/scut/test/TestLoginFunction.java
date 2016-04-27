package com.scut.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.knowbook.control.LoginController;
import com.scut.knowbook.model.User;
import com.scut.knowbook.model.User_info;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IUserService;


/** 声明用的是Spring的测试类 **/
@RunWith(SpringJUnit4ClassRunner.class)

/** 声明spring主配置文件位置，注意：以当前测试类的位置为基�?有多个配置文件以字符数组声明 **/
@ContextConfiguration(locations={"../../../configs/spring/spring-context.xml"})

/** 声明使用事务，不声明spring会使用默认事务管�?**/
@Transactional

/** 声明事务回滚，要不测试一个方法数据就没有了岂不很杯具，注意：插入数据时可注掉，不让事务回�?**/
@TransactionConfiguration(transactionManager="jpaTransactionManager",defaultRollback=true)
public class TestLoginFunction {
	
	@Resource(name = "userService")
	private IUserService userService;
	
	@Test
	public void registe(){
		String phoneNumber="18814122521";
		String password="1";
		JsonPacked jsonPacked=new JsonPacked();
		User user=new User();
		user.setPhoneNumber(phoneNumber);
		user.setPassword(password);
		
		User_info user_info=new User_info();
		user.setUser_info(user_info);
		userService.save(user);
		
		jsonPacked.setResult("registe,success");
		System.out.println(jsonPacked);
	}
}
	


package com.scut.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.knowbook.control.LoginController;
import com.scut.knowbook.model.BookList;
import com.scut.knowbook.model.Comments;
import com.scut.knowbook.model.Recommen_books;
import com.scut.knowbook.model.Son_comments;
import com.scut.knowbook.model.User;
import com.scut.knowbook.model.User_info;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IBookListService;
import com.scut.knowbook.service.ICommentsService;
import com.scut.knowbook.service.IRecommenBooksService;
import com.scut.knowbook.service.ISonCommentsService;
import com.scut.knowbook.service.IUserService;
import com.scut.knowbook.service.impl.BookListServiceImpl;
import com.scut.knowbook.service.impl.RecommenBooksServiceImpl;
import com.scut.knowbook.service.impl.SonCommentsServiceImpl;


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
	
	@Resource(name="recommenBooksService")
	private IRecommenBooksService recommenBooksService;
	
	@Resource(name="commentsService")
	private ICommentsService commentsService;
	
	@Resource
	private ISonCommentsService sonCommentsService;
	
	@Resource
	private IBookListService bookListService;
	
	@Test
	public void registe(){
		String phoneNumber="18814122529";
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			System.out.println("null");
		}
		BookList bookList=new BookList();
		bookList.setCreaterId(phoneNumber);
		bookList.setBookListName("辣鸡");
		bookList.getUsers().add(user);
		System.out.println(bookList.getUsers().size());
		bookListService.save(bookList);
	}
	
	@Test
	public void showBooklist(){
		Page<BookList> booklists=bookListService.findAllBookList(new PageRequest(0, 7));
		for(BookList booklist:booklists){
			System.out.println(booklist.getBookListName());
		}
	}
	
	@Test
	public void collectBoookList(){
		Long booklistId=1l;
		String phoneNumber="18814122529";
		BookList bookList=bookListService.findById(booklistId);
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user ==null){								//如果用户为空,返回json结果notloging表示未登录
			System.out.println("null");
		}
		if(bookList==null){								//如果找不到对应的书单id,返回id不存在
			System.out.println("null");
		}
		bookList.getUsers().add(user);					//用户收藏某个书单，则书单中增加该用户
		
		System.out.println(bookList.getUsers().contains(user));			//返回结果success，不返回实体类
		bookListService.save(bookList);
	}
	
	@Test
	public void lookMyBookList(){
		String phoneNumber="18814122529";
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			System.out.println("null");
		}
		Set<Recommen_books> recommen_books=user.getRecommen_books();
		if(recommen_books==null||recommen_books.isEmpty()){
			System.out.println("null");
		}
		for(Recommen_books recommen_book:recommen_books){
			System.out.println(recommen_book.getBookName());
		}
	}
	@Test
	public void findMostBooklist(){
		
		List<BookList> booklists=bookListService.findMostBookList();
		for(BookList booklist:booklists){
			System.out.println("----------------------");
			System.out.println(booklist.getBookListName()+" "+booklist.getUsers().size());
		}
	}
	@Test
	public void findAllBooklist(){
		Page<BookList> booklists=bookListService.findAllBookList(new PageRequest(0, 3));
		for(BookList booklist:booklists){
			System.out.println(booklist.getBookListName());
		}
	}
	@Test
	public void findTimeBooklist(){
		List<BookList> booklists=bookListService.findByCreateDateBetween(new Timestamp(System.currentTimeMillis()-604800000), new Timestamp(System.currentTimeMillis()));
		System.out.println(new Timestamp(System.currentTimeMillis()));
		System.out.println(new Timestamp(System.currentTimeMillis()-604800000));
		for(BookList booklist:booklists){
			System.out.println(booklist.getBookListName());
		}
	}
	
	@Test
	public void findHotBooklist(){
		List<BookList> booklists=bookListService.findHotBookList(new Timestamp(System.currentTimeMillis()-604800000), new Timestamp(System.currentTimeMillis()));
		System.out.println(new Timestamp(System.currentTimeMillis()));
		System.out.println(new Timestamp(System.currentTimeMillis()-604800000));
		for(BookList booklist:booklists){
			System.out.println(booklist.getBookListName());
		}
	}
}
	


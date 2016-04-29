package com.scut.knowbook.control;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.knowbook.model.BookList;
import com.scut.knowbook.model.Recommen_books;
import com.scut.knowbook.model.User;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IBookListService;
import com.scut.knowbook.service.IRecommenBooksService;
import com.scut.knowbook.service.IUserService;

@Controller
@RequestMapping(value="/booklist")
public class BookListControl {

	private Logger logger;
	
	@Resource(name="userService")
	private IUserService userService;
	@Resource(name="recommenBooksService")
	private IRecommenBooksService recommenBooksService;
	@Resource(name="bookListService")
	private IBookListService bookListService;
	/**
	 * addTobookList收藏书籍到用户书单
	 */
	@RequestMapping(value="/addTobookList", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object addTobookList(Long bookId,Long booklistId,HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");   //得到session里面的phoneNumber属性
		BookList bookList=bookListService.findById(bookId);
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
		}
		Recommen_books recommen_books=recommenBooksService.findById(booklistId);
		if(bookList==null||recommen_books==null){
			jsonPacked.setResult("id,null");
		}
		bookList.getRecommen_books().add(recommen_books);
		bookList.getUsers().add(user);
		bookListService.save(bookList);
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * createbooklist用于创建用户书单
	 */
	@RequestMapping(value="/createbooklist", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object createbooklist(String bookListName,HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");		//得到session里面的phoneNumber属性
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		BookList bookList=new BookList();
		bookList.setCreaterId(phoneNumber);
		bookList.setBookListName(bookListName);
		bookList.getUsers().add(user);
		bookListService.save(bookList);
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * fragmentbooklistWeek用于展示全部的书单
	 */
	@RequestMapping(value="/fragmentbooklistAll", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentbooklistAll() throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		Page<BookList> booklists=bookListService.findAllBookList(new PageRequest(0, 10));
		for(BookList booklist:booklists){
			jsonPacked.getResultSet().add(booklist);
		}
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * fragmentbooklistWorth展示收藏最多的书单
	 */
	@RequestMapping(value="/fragmentbooklistWorth", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentbooklistWorth() throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		List<BookList> booklists=bookListService.findMostBookList();
		for(BookList booklist:booklists){
			logger.info(booklist.getBookListName()+" "+booklist.getUsers().size());
		}
		jsonPacked.setResult("success");
		for(BookList booklist:booklists){
			jsonPacked.getResultSet().add(booklist);
		}
		return jsonPacked;
	}
	/**
	 * fragmentbooklistNew用于展示本周的书单
	 */
	@RequestMapping(value="/fragmentbooklistNew", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentbooklistNew() throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		List<BookList> booklists=bookListService.findByCreateDateBetween(new Timestamp(System.currentTimeMillis()-604800000), new Timestamp(System.currentTimeMillis()));
		for(BookList booklist:booklists){
			logger.info(booklist.getBookListName()+" "+booklist.getUsers().size());
		}
		jsonPacked.setResult("success");
		for(BookList booklist:booklists){
			jsonPacked.getResultSet().add(booklist);
		}
		return jsonPacked;
	}
	/**
	 * fragmentbooklistHot用于展示本周最热的书单
	 */
	@RequestMapping(value="/fragmentbooklistHot", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentbooklistHot() throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		List<BookList> booklists=bookListService.findHotBookList(new Timestamp(System.currentTimeMillis()-604800000), new Timestamp(System.currentTimeMillis()));
		for(BookList booklist:booklists){
			logger.info(booklist.getBookListName()+" "+booklist.getUsers().size());
		}
		jsonPacked.setResult("success");
		for(BookList booklist:booklists){
			jsonPacked.getResultSet().add(booklist);
		}
		return jsonPacked;
	}
	/**
	 * detailbooklist用于展示书单详情
	 */
	@RequestMapping(value="/detailbooklist", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object detailbooklist(Long booklistId) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		BookList bookList=bookListService.findById(booklistId);
		if(bookList==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		Set<Recommen_books> recommen_books=bookList.getRecommen_books();		//得到书单收藏的书籍
		if(recommen_books==null||recommen_books.isEmpty()){						//如果书籍为空
			jsonPacked.setResult("content,null");								//json返回结果null
			return jsonPacked;
		}
		for(Recommen_books recommen_book:recommen_books){						//for循环将每本书实体打包发送给客户端
			jsonPacked.getResultSet().add(recommen_book);						//客户端将书籍所需要的信息进行过滤，去掉不需要的属性
		}
		return jsonPacked;
	}
	/**
	 * CollectBooklist书单收藏
	 */
	@RequestMapping(value="/collectBooklist", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object collectBooklist(Long booklistId,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		BookList bookList=bookListService.findById(booklistId);
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber"); 		//得到session里面的phoneNumber属性
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user ==null){								//如果用户为空,返回json结果notloging表示未登录
			jsonPacked.setResult("notlogin");
		}
		if(bookList==null){								//如果找不到对应的书单id,返回id不存在
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		bookList.getUsers().add(user);					//用户收藏某个书单，则书单中增加该用户
		bookListService.save(bookList);
		jsonPacked.setResult("success");				//返回结果success，不返回实体类
		return jsonPacked;
	}
	/**
	 * noCollectBooklist取消收藏
	 */
	@RequestMapping(value="/noCollectBooklist", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object noCollectBooklist(Long booklistId,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		BookList bookList=bookListService.findById(booklistId);
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");		//得到session里面的phoneNumber属性
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user ==null){
			jsonPacked.setResult("notlogin");
		}
		if(bookList==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		bookList.getUsers().remove(user);				//取消收藏，去掉书单中某一个用户
		bookListService.save(bookList);
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * myCreateBooklist查看自己的书单
	 */
	@RequestMapping(value="/myBooklist", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object myBooklist(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");		//得到session里面的phoneNumber属性
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		Set<BookList> bookLists=user.getBookList();
		if(bookLists==null||bookLists.isEmpty()){
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		for(BookList bookList:bookLists){
			jsonPacked.getResultSet().add(bookList);
		}
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	
}

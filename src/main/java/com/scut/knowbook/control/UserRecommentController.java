package com.scut.knowbook.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.scut.knowbook.service.impl.RecommenBooksServiceImpl;

@Controller
@RequestMapping(value="/showBook")
public class UserRecommentController {

	
	/**
	 * 用于用户操作，包括用户查询自己或他人推荐过的书籍，用户查询自己收藏的书单以及读取每个书单相应的内容
	 */
	private Logger logger = Logger.getLogger(getClass());
	@Resource(name="userService")
	private IUserService userService;
	
	@Resource(name="bookListService")
	private IBookListService bookListService;
	
	@Resource(name="recommenBooksService")
	private IRecommenBooksService recommenBooksService;
	/**
	 * 用于用户插入新的推荐书籍，包括bookName,bookpicture,bookAuthor,bookClass,bookSummary,recommenReason||phoneNUmber这些属性
	 */
	@RequestMapping(value="/createshowbook",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody Object createshowbook(String bookName,String bookpicture,String bookAuthor,
			String bookClass,String bookSummary,String recommenReason,String phoneNumber)throws JsonGenerationException, JsonMappingException, IOException{
		JsonPacked jsonPacked=new JsonPacked();
		Recommen_books recommen_books=new Recommen_books();
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("user,null");
			return jsonPacked;
		}
		recommen_books.setBookLocation("1");
		recommen_books.setTitleImage(bookpicture);
		recommen_books.setBookName(bookName);
		recommen_books.setBookAuthor(bookAuthor);
		recommen_books.setBookClass(bookClass);
		recommen_books.setBookSummary(bookSummary);
		recommen_books.setRecommenReason(recommenReason);
		recommen_books.setRecommenerId(phoneNumber);
		user.getRecommen_books().add(recommen_books);
		recommen_books.setUser(user);
		recommenBooksService.save(recommen_books);
		userService.save(user);
		jsonPacked.getResultSet().add(recommen_books);
		return jsonPacked;
	}
	/**
	 * 用于用户查询自己或者他人推荐过的书籍
	 */
	@RequestMapping(value="/MyRecommentBooks", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object recomment(String phoneNumber) throws JsonGenerationException, JsonMappingException, IOException{
		User user=userService.findByPhoneNumber(phoneNumber);
		JsonPacked jsonPacked=new JsonPacked();
		if(user!=null){
			Set<Recommen_books> books=user.getRecommen_books();
			logger.info("用户id为"+phoneNumber+"的家伙推荐了这些书:");
			logger.info(books.toString());
			for(Recommen_books recommen_books:books){
				jsonPacked.getResultSet().add(recommen_books);
			}
			if (jsonPacked.getResult().isEmpty()) {
				jsonPacked.setResult("mybooks,null");
			}
			return jsonPacked;
		}
		else{
			jsonPacked.setResult("user,null");
			return jsonPacked;
		}
	}	
	
	/**
	 * 用于用户查询自己收藏的书单
	 */
	
	@RequestMapping(value="/MyCollectBooks", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object collect(String phoneNumber) throws JsonGenerationException, JsonMappingException, IOException{
		User user=userService.findByPhoneNumber(phoneNumber);
		JsonPacked jsonPacked=new JsonPacked();
		if(user!=null){
			Set<BookList> booklist=user.getBookList();
			if(!booklist.isEmpty()){
				for(BookList book:booklist){
					jsonPacked.getResultSet().add(book);
				}
				return jsonPacked;
			}
			else{
				jsonPacked.setResult("booklist,null");
				return jsonPacked;
			}
		}
		jsonPacked.setResult("user,null");
		return jsonPacked;
	}	
	
	/**
	 * 用于用户查看所有的书单
	 */
	@RequestMapping(value="/AllCollectBooks", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object collectAll() throws JsonGenerationException, JsonMappingException, IOException{
		JsonPacked jsonPacked=new JsonPacked();
		Set<BookList> bookLists=(HashSet<BookList>) bookListService.findAll();
		if(bookLists.isEmpty()){
			jsonPacked.setResult("booklists,null");
			return jsonPacked;
		}
		else{
			jsonPacked.getResultSet().add(bookLists);
		}
		return jsonPacked;
	}	
}

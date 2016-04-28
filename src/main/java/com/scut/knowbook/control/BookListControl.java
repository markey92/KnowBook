package com.scut.knowbook.control;

import java.awt.print.Book;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
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
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");
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
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");
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
	 * fragmentbooklistWeek用于展示本周的书单
	 */
	@RequestMapping(value="/fragmentbooklistWeek", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentbooklistWeek() throws JsonGenerationException, JsonMappingException, IOException{
		
		JsonPacked jsonPacked=new JsonPacked();
		Page<BookList> booklists=bookListService.findAllBookList(new PageRequest(0, 7));
		for(BookList booklist:booklists){
			jsonPacked.getResultSet().add(booklist);
		}
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package com.scut.knowbook.control;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

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
import org.springframework.web.multipart.MultipartFile;

import com.scut.knowbook.model.BookList;
import com.scut.knowbook.model.Recommen_books;
import com.scut.knowbook.model.User;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IBookListService;
import com.scut.knowbook.service.IFileUpLoadService;
import com.scut.knowbook.service.IRecommenBooksService;
import com.scut.knowbook.service.IUserService;

@Controller
@RequestMapping(value="/booklist")
public class BookListControl {

	private Logger logger = Logger.getLogger(getClass());
	
	@Resource(name="userService")
	private IUserService userService;
	@Resource(name="recommenBooksService")
	private IRecommenBooksService recommenBooksService;
	@Resource(name="bookListService")
	private IBookListService bookListService;
	@Resource(name="fileUpLoadService")
	private IFileUpLoadService fileUpLoadService;
	/**
	 * addTobookList收藏书籍到用户书单
	 */
	@RequestMapping(value="/addTobookList", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object addTobookList(Long bookId,Long booklistId,HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		
		logger.info("addTobooklist===>bookId为:"+bookId+",booklistId: "+booklistId);
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");   //得到session里面的phoneNumber属性
		BookList bookList=bookListService.findById(booklistId);
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		Recommen_books recommen_books=recommenBooksService.findById(bookId);
		if(bookList==null||recommen_books==null){
			logger.info("addTobooklist===>找不到id");
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		recommen_books.getBookList().add(bookList);
		bookList.getRecommen_books().add(recommen_books);
		
//		bookList.getUsers().add(user);
		
		bookListService.save(bookList);
		recommenBooksService.save(recommen_books);
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * 
	 */
	@RequestMapping(value="/removeFrombookList", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object removeFrombookList(Long bookId,HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		
		logger.info("removeFrombookList===>bookId为:"+bookId);
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");   //得到session里面的phoneNumber属性
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		Recommen_books recommen_books=recommenBooksService.findById(bookId);
		if(recommen_books==null){
			logger.info("removeFrombookList===>找不到id");
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		for(BookList bookList:user.getBookList()){
			if(bookList.getRecommen_books().contains(recommen_books)){
				recommen_books.getBookList().remove(bookList);
				recommenBooksService.save(recommen_books);
			}
		}
		
//		bookList.getUsers().add(user);
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * createbooklist用于创建用户书单
	 * @throws Exception 
	 */
	@RequestMapping(value="/createbooklist", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object createbooklist(@RequestParam("booklistPicture") MultipartFile booklistPicture,String booklistName,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");		//得到session里面的phoneNumber属性
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		logger.info(phoneNumber+"上传了文件"+booklistPicture.getOriginalFilename()+"并创建了书单");
		String url=fileUpLoadService.FileUpload(booklistPicture, System.currentTimeMillis()+phoneNumber+".jpg"); 
		if(url==null||url.equals("")||url.isEmpty()){
			logger.info("file为空");
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		BookList bookList=new BookList();
		bookList.setBooklistPicture(url);
		bookList.setCreaterId(phoneNumber);
		bookList.setBookListName(booklistName);
		bookList.getUsers().add(user);
		bookListService.save(bookList);
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * fragmentbooklistWeek用于展示全部的书单
	 */
	@RequestMapping(value="/fragmentbooklistAll", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentbooklistAll(int page,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		logger.info("请求页码为："+page);
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		JsonPacked jsonPacked=new JsonPacked();
		Page<BookList> booklists=bookListService.findAllBookList(new PageRequest(page, 10));
		int peopleCount,bookCount;
		for(BookList booklist:booklists){
			jsonPacked.getResultSet().add(booklist);
			
			peopleCount=booklist.getUsers().size();
			bookCount=booklist.getRecommen_books().size();
			Map<String, Integer> map=new ConcurrentHashMap<String, Integer>();
			for(User user:booklist.getUsers()){
				if(user.getPhoneNumber().equals(phoneNumber)){
					map.put("isCollect", 1);
					break;
				}
			}
			if(map.get("isCollect")==null){
				map.put("isCollect", 0);
			}
			map.put("peopleCount", peopleCount);
			map.put("bookCount", bookCount);
			
			jsonPacked.getResultSet().add(map);
		}
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * fragmentbooklistWorth展示收藏最多的书单
	 */
	@RequestMapping(value="/fragmentbooklistWorth", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentbooklistWorth(int page,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		logger.info("请求页码为"+page);
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		JsonPacked jsonPacked=new JsonPacked();
		List<BookList> booklists=bookListService.findMostBookList(new PageRequest(page, 10));
		int peopleCount,bookCount;
		for(BookList booklist:booklists){
			jsonPacked.getResultSet().add(booklist);
			
			peopleCount=booklist.getUsers().size();
			bookCount=booklist.getRecommen_books().size();
			Map<String, Integer> map=new ConcurrentHashMap<String, Integer>();
			for(User user:booklist.getUsers()){
				if(user.getPhoneNumber().equals(phoneNumber)){
					map.put("isCollect", 1);
					break;
				}
			}
			if(map.get("isCollect")==null){
				map.put("isCollect", 0);
			}
			map.put("peopleCount", peopleCount);
			map.put("bookCount", bookCount);
			
			jsonPacked.getResultSet().add(map);
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
	public @ResponseBody Object fragmentbooklistNew(Integer page,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		if(page==null){
			page=0;
		}
		logger.info("请求页码为"+page);
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		JsonPacked jsonPacked=new JsonPacked();
		List<BookList> booklists=bookListService.findByCreateDateBetween(new Timestamp(System.currentTimeMillis()-604800000), new Timestamp(System.currentTimeMillis()),new PageRequest(page, 10));
		for(BookList booklist:booklists){
			logger.info(booklist.getBookListName()+" "+booklist.getUsers().size());
		}
		int peopleCount,bookCount;
		for(BookList booklist:booklists){
			jsonPacked.getResultSet().add(booklist);
			
			peopleCount=booklist.getUsers().size();
			bookCount=booklist.getRecommen_books().size();
			Map<String, Integer> map=new ConcurrentHashMap<String, Integer>();
			for(User user:booklist.getUsers()){
				if(user.getPhoneNumber().equals(phoneNumber)){
					map.put("isCollect", 1);
					break;
				}
			}
			if(map.get("isCollect")==null){
				map.put("isCollect", 0);
			}
			map.put("peopleCount", peopleCount);
			map.put("bookCount", bookCount);
			
			jsonPacked.getResultSet().add(map);
		}
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * fragmentbooklistHot用于展示本周最热的书单
	 */
	@RequestMapping(value="/fragmentbooklistHot", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentbooklistHot(Integer page,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		if(page==null){
			page=0;
		}
		logger.info("请求页码为"+page);
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		JsonPacked jsonPacked=new JsonPacked();
		List<BookList> booklists=bookListService.findHotBookList(new Timestamp(System.currentTimeMillis()-604800000), new Timestamp(System.currentTimeMillis()),new PageRequest(page, 10));
		for(BookList booklist:booklists){
			logger.info(booklist.getBookListName()+" "+booklist.getUsers().size());
		}
		int peopleCount,bookCount;
		for(BookList booklist:booklists){
			jsonPacked.getResultSet().add(booklist);
			
			peopleCount=booklist.getUsers().size();
			bookCount=booklist.getRecommen_books().size();
			Map<String, Integer> map=new ConcurrentHashMap<String, Integer>();
			for(User user:booklist.getUsers()){
				if(user.getPhoneNumber().equals(phoneNumber)){
					map.put("isCollect", 1);
					break;
				}
			}
			if(map.get("isCollect")==null){
				map.put("isCollect", 0);
			}
			map.put("peopleCount", peopleCount);
			map.put("bookCount", bookCount);
			
			jsonPacked.getResultSet().add(map);
		}
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * detailbooklist用于展示书单详情
	 */
	@RequestMapping(value="/detailbooklist", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object detailbooklist(Long booklistId) throws JsonGenerationException, JsonMappingException, IOException{
		
		logger.info("请求的booklistId为："+booklistId);
		JsonPacked jsonPacked=new JsonPacked();
		BookList bookList=bookListService.findById(booklistId);
		if(bookList==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		Set<Recommen_books> recommen_books=bookList.getRecommen_books();		//得到书单收藏的书籍
		if(recommen_books==null||recommen_books.isEmpty()){						//如果书籍为空
			logger.info("content is null");
			jsonPacked.setResult("content,null");								//json返回结果null
			return jsonPacked;
		}
		jsonPacked.setResult("success");
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
	/**
	 * 删除自己的书单
	 */
	@RequestMapping(value="/deleteBooklist", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object deleteBooklist(Long booklistId,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		logger.info("请求删除的booklistId为："+booklistId);
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");		//得到session里面的phoneNumber属性
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		BookList bookList=bookListService.findById(booklistId);
		if(bookList==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		//删除书单，要删除所有收藏他的用户的关系，要删除书单所有的收藏书籍的关系，最后才能删除书单
		bookList.getUsers().removeAll(bookList.getUsers());
		for(Recommen_books recommen_book:bookList.getRecommen_books()){
			recommen_book.getBookList().remove(bookList);
		}
		bookListService.delete(bookList);
		jsonPacked.setResult("success");
		logger.info("成功删除id为:"+booklistId+" 的书单");
		return jsonPacked;
	}
	/**
	 * detailmyCreateBooklist查看书单详情
	 */
	@RequestMapping(value="/detailmyCreateBooklist", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object detailmyCreateBooklist(Long booklistId,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		
		logger.info("请求的booklistId为："+booklistId);
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");		//得到session里面的phoneNumber属性
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		BookList bookList=bookListService.findById(booklistId);
		if(bookList==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(bookList);
		return jsonPacked;
	}
}

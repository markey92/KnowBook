package com.scut.knowbook.control;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.scut.knowbook.model.Comments;
import com.scut.knowbook.model.Recommen_books;
import com.scut.knowbook.model.Son_comments;
import com.scut.knowbook.model.User;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IBookListService;
import com.scut.knowbook.service.ICommentsService;
import com.scut.knowbook.service.IFileUpLoadService;
import com.scut.knowbook.service.IRecommenBooksService;
import com.scut.knowbook.service.ISonCommentsService;
import com.scut.knowbook.service.IUserService;

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
	
	@Resource(name="commentsService")
	private ICommentsService commentsService;
	
	@Resource(name="sonCommentsService")
	private ISonCommentsService sonCommentsService;
	
	@Resource(name="fileUpLoadService")
	private IFileUpLoadService fileUpLoadService;
	/**
	 * 用于用户插入新的推荐书籍，包括bookName,bookpicture,bookAuthor,bookClass,bookSummary,recommenReason||phoneNUmber这些属性
	 * @throws Exception 
	 */
	@RequestMapping(value="/createshowbook",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody Object createshowbook(Recommen_books recommen_books,@RequestParam("bookPicture") MultipartFile bookPicture,HttpServletRequest request)throws Exception{
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		logger.info("用户"+phoneNumber+"上传了"+bookPicture.getOriginalFilename()+"并试图创建推荐书籍");
		String url=fileUpLoadService.FileUpload(bookPicture, System.currentTimeMillis()+phoneNumber+".jpg");
		if(url==null||url.isEmpty()||url.equals("")){
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		recommen_books.setTitleImage(url);
		recommen_books.setUser(user);
		recommenBooksService.save(recommen_books);
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(recommen_books);
		Map<String, Integer> map=new ConcurrentHashMap<String, Integer>();
		map.put("isCollect", 0);
		map.put("commentCount", 0);
		jsonPacked.getResultSet().add(map);
		return jsonPacked;
	}
	/**
	 * 推荐书籍展示,用于展示所有用户推荐书籍的数据，一次推送十条数据
	 */
	@RequestMapping(value="/fragmentshow", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentshow(HttpServletRequest request,int page) throws JsonGenerationException, JsonMappingException, IOException{
		
		logger.info("请求页码为："+page);
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		User user=userService.findByPhoneNumber(phoneNumber);
		JsonPacked jsonPacked=new JsonPacked();
		if(user!=null){
			Page<Recommen_books> recommen_books=recommenBooksService.findAll(new PageRequest(page, 10));
			int numOfComments;
			for(Recommen_books recommen_book:recommen_books){
				jsonPacked.getResultSet().add(recommen_book);
				numOfComments=recommen_book.getComments().size();
				Map<String, Object> map=new ConcurrentHashMap<String, Object>();
				map.put("userName",recommen_book.getUser().getUserName().toString());
				map.put("sex",recommen_book.getUser().getSex().toString());
				map.put("numOfComments", numOfComments);
				outterLoop: for(BookList bookList:user.getBookList())
					for(Recommen_books recommen_books2:bookList.getRecommen_books()){
						if(recommen_book.getId()==recommen_books2.getId()){
							map.put("isCollect", 1);
							break outterLoop; 
						}
					}
				if(map.get("isCollect")==null){
					map.put("isCollect", 0);
				}
				jsonPacked.getResultSet().add(map);
			}
			jsonPacked.setResult("success");
			return jsonPacked;
		}
		else{
			jsonPacked.setResult("null");
			return jsonPacked;
		}
	}	
	/**
	 * detailshowbook用于展示一本推荐书籍的具体内容
	 */
	@RequestMapping(value="/detailshowbook", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object detailshowbook(Long id) throws JsonGenerationException, JsonMappingException, IOException{

		logger.info("传送的id:"+id);
		JsonPacked jsonPacked=new JsonPacked();
		Recommen_books recommen_books=recommenBooksService.findById(id);
		if(recommen_books==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(recommen_books);
		Map<String, Object> rebooks=new ConcurrentHashMap<String, Object>();
		rebooks.put("commentCount", recommen_books.getComments().size());
		rebooks.put("bookScore", recommen_books.getBookScore());
		jsonPacked.getResultSet().add(rebooks);											//返回评论数量
		Page<Comments> comments=commentsService.findBy(id,new PageRequest(0, 2));		//这里应该用分页调用，第一次取出两条评论，之后每页取出十条评论
		if(comments!=null){
			for(Comments comment:comments){
				jsonPacked.getResultSet().add(comment);
				Map<String, Object> map=new ConcurrentHashMap<String, Object>();		//返回评论者信息
				if(comment.getUser().getUserName()==null){
					map.put("commentUser", "null");
				}
				else{
					map.put("commentUser", comment.getUser().getUserName());
				}
				if(comment.getUser().getUser_info().getHeadPicture()==null){
					map.put("headPicture", "null");
				}
				else{
					map.put("headPicture", comment.getUser().getUser_info().getHeadPicture());
				}
				map.put("sonCommentCount",comment.getSon_comments().size());
				jsonPacked.getResultSet().add(map);
			}
		}
		return jsonPacked;
	}	
	/**
	 * 给出每页十条对应书籍id的评论
	 */
	@RequestMapping(value="/showbookComment", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object showbookComment(Long id,int page) throws JsonGenerationException, JsonMappingException, IOException{

		JsonPacked jsonPacked=new JsonPacked();
		Recommen_books recommen_books=recommenBooksService.findById(id);
		if(recommen_books==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		jsonPacked.setResult("success");
		Page<Comments> comments=commentsService.findBy(id,new PageRequest(page, 10));		//这里应该用分页调用，第一次取出两条评论，之后每页取出十条评论
		if(comments!=null){
			for(Comments comment:comments){
				jsonPacked.getResultSet().add(comment);
			}
		}
		return jsonPacked;
	}	
	/**
	 * writeshowbookComment用于对某件推荐书籍写出自己的评论
	 */
	
	@RequestMapping(value="/writeshowbookComment", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object writeshowbookComment(Long bookId,Comments comments,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		JsonPacked jsonPacked=new JsonPacked();
		Recommen_books recommen_books=recommenBooksService.findById(bookId);
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		User user=userService.findByPhoneNumber(phoneNumber);
		if(recommen_books==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		if(comments!=null){
			logger.info(comments.getCommentContent()+":"+comments.getCommentScore());
			comments.setRecommen_books(recommen_books);
			double bookScore=comments.getRecommen_books().getBookScore();
			comments.getRecommen_books().setBookScore((bookScore+comments.getCommentScore())/2);
			comments.setUser(user);
			commentsService.save(comments);
			jsonPacked.setResult("success");  			//客户端写入自己的评论数据之后，返回success，后请求/detailshowbook进行评论刷新
			Map<String, Object> map=new ConcurrentHashMap<String, Object>();
			map.put("bookScore", comments.getRecommen_books().getBookScore());
			map.put("commentCount", comments.getRecommen_books().getComments().size());
			jsonPacked.getResultSet().add(map);
			return jsonPacked;
		}
		else{
			logger.info("发过来的评论不全");
			jsonPacked.setResult("fail");
			return jsonPacked;
		}
	}	
	/**
	 * detailshowbookComment展现子评论
	 */
	@RequestMapping(value="/detailshowbookComment", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object detailshowbookComment(Long commentId,int page,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		JsonPacked jsonPacked=new JsonPacked();
		long id=commentId;
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		Comments comments=commentsService.findById(id);
		if(comments==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
//		Map<String, Object> comb=new ConcurrentHashMap<String, Object>();
//		comb.put("CommentUser", comments.getUser());
//		jsonPacked.getResultSet().add(comb);
		Page<Son_comments> son_comments=sonCommentsService.findByCommentsId(commentId, new PageRequest(page, 10));
		for(Son_comments son_comment:son_comments){
			jsonPacked.getResultSet().add(son_comment);
			Map<String, Object> map=new ConcurrentHashMap<String, Object>();
			String sonCommentUser=son_comment.getUser().getUserName();
			String headPicture=son_comment.getUser().getUser_info().getHeadPicture();
			if(sonCommentUser==null||sonCommentUser.isEmpty()){
				logger.info("用户名不存在，改用phoneNumber作为username");
				sonCommentUser=son_comment.getUser().getPhoneNumber();
			}
			map.put("sonCommentUser", sonCommentUser);
			map.put("headPicture", headPicture);
			jsonPacked.getResultSet().add(map);
		}
		jsonPacked.setResult("success");
		return jsonPacked;
	}	
	/**
	 * 创建对应评论id的子评论writeshowbookSonComment
	 */
	@RequestMapping(value="/writeshowbookSonComment", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object writeshowbookSonComment(Long commentId,String commentContent,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		JsonPacked jsonPacked=new JsonPacked();
		long id=commentId;
		String phoneNumber=(String)request.getSession().getAttribute("phoneNumber");
		if(phoneNumber==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		Comments comments=commentsService.findById(id);
		User user=userService.findByPhoneNumber(phoneNumber);
		if(comments==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		Son_comments son_comments=new Son_comments();
		son_comments.setCommentContent(commentContent);
		son_comments.setUser(user);
		son_comments.setComments(comments);
		sonCommentsService.save(son_comments);
		jsonPacked.setResult("success");
		return jsonPacked;
	}	
	/**
	 * 用于用户给一个评论点赞，detailCommentClickOne
	 */
	@RequestMapping(value="/detailCommentClickOne",method=RequestMethod.GET,produces="text/html;charset=utf-8")
	public @ResponseBody Object detailCommentClickOne(Long commentId,boolean likeOrDisLike)throws JsonGenerationException,JsonMappingException,IOException{
		JsonPacked jsonPacked=new JsonPacked();
		Long id=commentId;
		Comments comments=commentsService.findById(id);
		if(comments==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		if(likeOrDisLike==true){
			int numOfLike=comments.getNumOfLike();
			comments.setNumOfLike(++numOfLike);
		}
		else{
			int numOfDisLike=comments.getNumOfDislike();
			comments.setNumOfDislike(++numOfDisLike);
		}
		commentsService.save(comments);
		jsonPacked.setResult("success");
		return jsonPacked;
	}
	/**
	 * myshowBook查看自己推荐过的书籍	
	 */
	@RequestMapping(value="/myshowBook",method=RequestMethod.GET,produces="text/html;charset=utf-8")
	public @ResponseBody Object myshowBook(HttpServletRequest request)throws JsonGenerationException,JsonMappingException,IOException{
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("notlogin");
			return jsonPacked;
		}
		Set<Recommen_books> recommen_books=user.getRecommen_books();
		if(recommen_books==null||recommen_books.isEmpty()){
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		for(Recommen_books recommen_book:recommen_books){
			jsonPacked.getResultSet().add(recommen_book);
		}
		jsonPacked.setResult("success");
		return jsonPacked;
	}
}

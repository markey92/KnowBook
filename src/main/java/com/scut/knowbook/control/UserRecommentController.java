package com.scut.knowbook.control;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.knowbook.model.BookList;
import com.scut.knowbook.model.Comments;
import com.scut.knowbook.model.Recommen_books;
import com.scut.knowbook.model.Son_comments;
import com.scut.knowbook.model.User;
import com.scut.knowbook.model.OP.JsonPacked;
import com.scut.knowbook.service.IBookListService;
import com.scut.knowbook.service.ICommentsService;
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
	/**
	 * 用于用户插入新的推荐书籍，包括bookName,bookpicture,bookAuthor,bookClass,bookSummary,recommenReason||phoneNUmber这些属性
	 */
	@RequestMapping(value="/createshowbook",method=RequestMethod.POST, produces = "text/html;charset=utf-8")
	public @ResponseBody Object createshowbook(Recommen_books recommen_books,HttpServletRequest request)throws JsonGenerationException, JsonMappingException, IOException{
		JsonPacked jsonPacked=new JsonPacked();
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		User user=userService.findByPhoneNumber(phoneNumber);
		if(user==null){
			jsonPacked.setResult("null");
			return jsonPacked;
		}
		recommen_books.setUser(user);
		recommenBooksService.save(recommen_books);
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(recommen_books);
		return jsonPacked;
	}
	/**
	 * 推荐书籍展示,用于展示所有用户推荐书籍的数据，一次推送十条数据
	 */
	@RequestMapping(value="/fragmentshow", method = RequestMethod.GET,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object fragmentshow(HttpServletRequest request,int page) throws JsonGenerationException, JsonMappingException, IOException{
		String phoneNumber=(String) request.getSession().getAttribute("phoneNumber");
		User user=userService.findByPhoneNumber(phoneNumber);
		JsonPacked jsonPacked=new JsonPacked();
		if(user!=null){
			Page<Recommen_books> recommen_books=recommenBooksService.findAll(new PageRequest(page, 10));
			for(Recommen_books recommen_book:recommen_books){
				jsonPacked.getResultSet().add(recommen_book);
				int numOfComments=recommen_book.getComments().size();
				int numOfCollect=recommen_book.getBookList().size();
				jsonPacked.getResultSet().add(numOfCollect);
				jsonPacked.getResultSet().add(numOfComments);
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

		JsonPacked jsonPacked=new JsonPacked();
		Recommen_books recommen_books=recommenBooksService.findById(id);
		if(recommen_books==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		jsonPacked.setResult("success");
		jsonPacked.getResultSet().add(recommen_books);
		Page<Comments> comments=commentsService.findBy(id,new PageRequest(0, 2));		//这里应该用分页调用，第一次取出两条评论，之后每页取出十条评论
		if(comments!=null){
			for(Comments comment:comments){
				jsonPacked.getResultSet().add(comment);
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
	public @ResponseBody Object writeshowbookComment(Long id,Comments comments) throws JsonGenerationException, JsonMappingException, IOException{
		JsonPacked jsonPacked=new JsonPacked();
		Recommen_books recommen_books=recommenBooksService.findById(id);
		if(recommen_books==null){
			jsonPacked.setResult("id,null");
			return jsonPacked;
		}
		if(comments!=null){
			comments.setRecommen_books(recommen_books);
			commentsService.save(comments);
			jsonPacked.setResult("success");  			//客户端写入自己的评论数据之后，返回success，后请求/detailshowbook进行评论刷新
		}
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

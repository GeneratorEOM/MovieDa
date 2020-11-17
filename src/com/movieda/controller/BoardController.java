package com.movieda.controller;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.movieda.beans.BoardBean;
import com.movieda.beans.CommentBean;
import com.movieda.beans.PageBean;
import com.movieda.service.BoardService;

@RestController
@RequestMapping("/api/board")
public class BoardController {

	@Autowired
	BoardService boardService;

	

	@GetMapping("/list")
	public Map<String, Object> getBoardList(@RequestParam(value = "page", defaultValue = "1") int page,
										HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<BoardBean> boardList = boardService.getBoardList(page);
		
		PageBean pageBean = boardService.getContentCnt(page);
		
		map.put("boardList", boardList);
		map.put("pageBean", pageBean);
		
		return map;
	}
	

	@PostMapping("/write")
	public int write(BoardBean boardBean) {		
		return boardService.writeBoard(boardBean);
	}

	@GetMapping("/detail")
	public BoardBean detail(@RequestParam(value = "board_idx") int board_idx) {
		return boardService.detailBoard(board_idx);
	}

	@PutMapping("/modify")
	public void modify(BoardBean boardBean) {
		System.out.println("content: " + boardBean.getBoard_content());
		boardService.modifyBoard(boardBean);
	}

	@DeleteMapping("/delete")
	public void delete(@RequestParam(value = "board_idx") int board_idx) {
		System.out.println("board_idx: " + board_idx);
		boardService.deleteBoard(board_idx);
	}
	
	@GetMapping("/comment/list")
	public List<CommentBean> commentList(@RequestParam(value = "board_idx") int board_idx) {
		return boardService.getCommentList(board_idx);
	}
	
	@PostMapping("/comment/write")
	public void writeComment(@RequestBody CommentBean commentBean) {
		boardService.writeComment(commentBean);
	}
	
	@PutMapping("/comment/modify")
	public void modifyComment(@RequestBody CommentBean commentBean) {
		boardService.modifyComment(commentBean);
	}
	
	@DeleteMapping("/comment/delete")
	public void deleteComment(@RequestParam(value = "comment_idx") int comment_idx) {
		boardService.deleteComment(comment_idx);
	}
	
	

	// 파일 다운 로직인데 잘모르겠음..
	@RequestMapping("/fileDown")
	public void fileDownload(@RequestParam(value = "file_name") String file_name, HttpServletRequest request,
			HttpServletResponse response) {
		String file_path = request.getServletContext().getRealPath("/resources/upload");
		String full_path = file_path + "\\"  + file_name;
		

		byte[] b = new byte[4096];
		// File 파일 객체 생성해서 파일정보 크기, 속성, 존재여부,..

		File oFile = new File(full_path);

		try {
			// 파일 타입 확인
			String sMimeType = request.getServletContext().getMimeType(full_path);
			// 파일 타입이 확인이 안되면
			// octet-stream은 8비트로 된 일련의 데이터를 뜻합니다. 지정되지 않은 파일 형식을 의미합니다.
			if (sMimeType == null)
				sMimeType = "application/octet-stream";
			response.setContentType(sMimeType);
			// 브라우저별로 파일 한글처리
			String userAgent = request.getHeader("User-Agent");
			boolean ie = (userAgent.indexOf("MSIE") > -1) || (userAgent.indexOf("Trident") > -1);
			String sEncoding = null;
			if (ie) {
				sEncoding = URLEncoder.encode(file_name, "utf-8").replaceAll("\\+", "%20");
				System.out.println(sEncoding);
			} else {
				sEncoding = new String(file_name.getBytes("utf-8"), "8859_1");
				System.out.println(sEncoding);
				// 한글 업로드 (이 부분이 한글 파일명이 깨지는 것을 방지해 줍니다.)
//		    String sEncoding = new String(file_name.getBytes("utf-8"),"8859_1");
				// System.out.println(sEncoding);
				// 서버 -> 클라이언트 설정 바꿈 => 파일첨부 다운
				response.setHeader("Content-Disposition", "attachment; filename= " + sEncoding);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}


		try {
			ServletOutputStream sos = response.getOutputStream();
			// 파일 입력받기
			FileInputStream in = new FileInputStream(oFile);
			int numRead;
			// 첨부파일을 입력받아서 파일다운 되게 설정변경 => 출력
			// 바이트 배열b의 0번 부터 numRead번 까지 브라우저로 출력
			while ((numRead = in.read(b, 0, b.length)) != -1) {
				sos.write(b, 0, numRead);
			}
			sos.flush();
			sos.close();
			in.close();
		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	// 파일을 업로드 하지 않을 경우 MultipartFile 객체에 String null 이 들어가서 타입이 맞지 않음 
	// MultipartFile 객체에 String 타입으로 변수가 들어오면 아래 메소드가 실행되고 Object value 로 셋팅해줌
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
	    binder.registerCustomEditor(MultipartFile.class, new PropertyEditorSupport() {
	        @Override
	        public void setAsText(String text) {	        
	            setValue(null);
	        }
	    });
	}
}

package com.movieda.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieda.beans.BoardBean;
import com.movieda.beans.CommentBean;
import com.movieda.beans.PageBean;
import com.movieda.dao.BoardDAO;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {

	@Value("${path.upload}")
	private String path_upload;
	
	@Value("${page.listCnt}")
	private int page_listCnt;
	
	@Value("${page.paginationCnt}")
	private int page_paginationCnt;

	@Autowired
	BoardDAO boardDAO;

	// 파일 저장을 위한 메서드
	// 파일이름에 날짜를 더해서 중복을 방지한다
	private String saveUploadFile(MultipartFile upload_file) {

		String file_name = System.currentTimeMillis() + upload_file.getOriginalFilename();
		try {
			upload_file.transferTo(new File(path_upload + "/" + file_name));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file_name;
	}

	public List<BoardBean> getBoardList(int page) {
		
		/*
		RowBounds(row 인덱스번호(0부터) , 가져올 페이지 수(고정값))
		RowBounds(0, 10) -> 0부터 10개 가져오겠다 / (10, 10) -> 10부터 10개 / (20, 10) ....
		페이지는 1부터 시작하니까 (page - 1) * listCnt => 인덱스가 0 / 10 / 20 ... 로 맞춰짐
		그럼 결과는 1페이지 -> 0부터 10개 / 2페이지 -> 10부터 10개 / ...
		*/
		int startRowIndex = (page - 1) * page_listCnt;
		RowBounds rowBounds = new RowBounds(startRowIndex, page_listCnt);
		
		return boardDAO.getBoardList(rowBounds);
		
	}

	public int writeBoard(BoardBean boardBean) {
		System.out.println("실행되냐");
		

		// 파일이 있으면
		if (boardBean.getUpload_file() != null) {
			// 파일 저장
			MultipartFile upload_file = boardBean.getUpload_file();
			// 디비에 저장될 변수 이름 생성
			String file_name = saveUploadFile(upload_file);
			boardBean.setBoard_file(file_name);
		}
		// write 성공 1 실패 0 반환
		int writeSuccess = boardDAO.writeBoard(boardBean);
		if (writeSuccess == 1)
			return 1;
		else
			return 0;
	}

	public BoardBean detailBoard(int board_idx) {
		return boardDAO.detailBoard(board_idx);
	}

	public void modifyBoard(BoardBean boardBean) {
		// 파일이 있으면
		if (boardBean.getUpload_file() != null) {
			// 파일 저장
			MultipartFile upload_file = boardBean.getUpload_file();
			// 디비에 저장될 변수 이름 생성
			String file_name = saveUploadFile(upload_file);
			boardBean.setBoard_file(file_name);
		}

		boardDAO.modifyBoard(boardBean);

	}

	public void deleteBoard(int board_idx) {
		boardDAO.deleteBoard(board_idx);
	}
	
	public PageBean getContentCnt(int currentPage) {
		int contentCnt = boardDAO.getContentCnt();
		PageBean pageBean = new PageBean(contentCnt, currentPage, page_listCnt, page_paginationCnt);
		return pageBean;
	}

	public List<CommentBean> getCommentList(int board_idx) {
		return boardDAO.getCommentList(board_idx);
	}

	public void writeComment(CommentBean commentBean) {
		boardDAO.writeComment(commentBean);
		
	}

	public void modifyComment(CommentBean commentBean) {
		boardDAO.modifyComment(commentBean);
		
	}

	public void deleteComment(int comment_idx) {
		boardDAO.deleteComment(comment_idx);
		
	}

}

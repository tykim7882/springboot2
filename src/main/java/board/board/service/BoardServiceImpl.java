package board.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.common.FileUtils;
import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
//@Transactional
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		
		// 1. 게시글 등록
		boardMapper.insertBoard(board);
		
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
		
//		
//		// 파일 서버에 저장 
//		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
//			
//			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
//			String name;
//			while(iterator.hasNext()) {
//				name = iterator.next();
//				//log.debug("file name ====> " + name);
//				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
//				for(MultipartFile multipartFile : list) {
//					log.debug("start file information ===============");
//					log.debug("file name : " + multipartFile.getOriginalFilename());
//					log.debug("file size : " + multipartFile.getSize());
//					log.debug("file content-type : " + multipartFile.getContentType());
//					log.debug("end file information. =================");
//					
//				} 
//				
//				fileUtils.parseFileInfo(boardIdx, multipartHttpServletRequests);
//			}
//		}
		
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		
		
		// 1. 게시글 
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		
		// 2. 첨부파일
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
		// 3. 조회수 증가
		boardMapper.updateHitCount(boardIdx);
				
		return board;

	}

	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
		
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);
		
	}

	@Override
	public BoardFileDto selectBoardFileInfomation(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInfomation(idx, boardIdx);
	}

}

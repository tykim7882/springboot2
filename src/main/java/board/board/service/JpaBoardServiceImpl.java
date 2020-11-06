package board.board.service;

import java.util.List;
import java.util.Optional;

import javax.swing.border.Border;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.common.FileUtils;
import board.board.dto.BoardFileDto;
import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;
import board.board.repository.JpaBoardRepository;

@Service
public class JpaBoardServiceImpl implements JpaBoardService {

	@Autowired
	JpaBoardRepository jpaBoardReposory;
	
	@Autowired
	FileUtils fileUtils;
	
	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		return jpaBoardReposory.findAllByOrderByBoardIdxDesc(); 
	}

	@Override
	public void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		board.setCreatorId("admin");
		
		List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			board.setFileList(list);
		}
		
		jpaBoardReposory.save(board);

	}

	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
		Optional<BoardEntity> optional = jpaBoardReposory.findById(boardIdx);
		if(optional.isPresent()) {
			BoardEntity board = optional.get();
			board.setHitCnt(board.getHitCnt()+1);
			jpaBoardReposory.save(board);
			
			return board;
		}else {
			throw new NullPointerException();
		}
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		jpaBoardReposory.deleteById(boardIdx);
	}

	@Override
	public BoardFileEntity selectBoardFileInformation(int boardIdx, int idx) throws Exception {
		BoardFileEntity boardFile = jpaBoardReposory.findBoardFile(boardIdx, idx);
		return boardFile;
	}

}

package board.board.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="BoardDto : 게시글 내용", description = "게시글 내용")
@Data
public class BoardDto {

	@ApiModelProperty(value="게시글 번호")
	private int boardIdx; 
	
	@ApiModelProperty(value="게시글 제목")
	private String title;
	
	@ApiModelProperty(value="게시글 내용")
	private String contents;
	
	@ApiModelProperty(value="게시글 조회수")
	private int hitCnt;
	
	@ApiModelProperty(value="게시글 작성일시")
	private String createdDatetime;
	
	@ApiModelProperty(value="게시글 작성자 아이디")
	private String creatorId;
	
	@ApiModelProperty(value="게시글 수정일시")
	private String updatedDatetime;
	
	@ApiModelProperty(value="게시글 수정자 아이디")
	private String updatorId;
	
	@ApiModelProperty(value="게시글 삭제여부")
	private String deletedYn;
	
	@ApiModelProperty(value="게시글 첨부파일목록")
	private List<BoardFileDto> fileList;
}

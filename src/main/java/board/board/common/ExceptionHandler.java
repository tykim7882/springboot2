package board.board.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

	// 해당 메서드에서 처리할 예외를 지정
	// 실제 프로젝트에서는 다양한 예외처리 필요 
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		
		// 예외를 보여줄 화면
		ModelAndView mav = new ModelAndView("/error/error_default");
		mav.addObject("exception", exception);
		
		log.error("exception", exception);
		
		return mav;
	}
	
}

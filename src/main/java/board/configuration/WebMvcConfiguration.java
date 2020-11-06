package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.board.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}
	
//	@Bean
//	public CharacterEncodingFilter characterEncodingFilter(){
//		
//		// CharacterEncodingFilter는 스프링이 제공하는 클래스로 웹에서 주고받는 데이터의 헤더값을 UTF-8로 인코딩해줌
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true); //  기본값 false
//		// HttpServletRequest의 getcharacterEncoding 메서드의 반환값이  null이 아닌 경우에만 인코딩을 변경하지 않음.
//			
//		return characterEncodingFilter;
//	}
//	
//	@Bean
//	public HttpMessageConverter<String> responseBodyConverter(){
//		// StringHttpMessageConverter는 @ResponseBody 를 이용하여 결과물을 출력할 때 그 결과물을 UTF-8로 설정
//		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); //5 * 1024 * 1024 (5mb)
		return commonsMultipartResolver;
	}

}

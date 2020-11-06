package board.board.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAspect {
	
	// transaction 설정시 사용되는 값을 상수로 선언
	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	private static final String AOP_TRANSACTION_EXPRESSION = "execution(* board..service.*Impl.*(..))";
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public TransactionInterceptor transactionAdvice() {
		
		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		
		// 트랜잭션의 이름 설정( 트랜잭션 모니터에서 트랜잭선 이름으로 확인 가능)
		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
		// 트랜잭션 롤백 룰. Exception.class를 등록하면 어떤 예외가 발생해도 롤백이 수행됨
		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		
		source.setTransactionAttribute(transactionAttribute);
		return new TransactionInterceptor(transactionManager, source);
	}
	
	@Bean
	public Advisor transactionAdviceAdvisor(){
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		// AOP 포인트컷 설정. 
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
}

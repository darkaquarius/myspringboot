package boot;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Created by huishen on 17/5/31.
 *
 */
public class SpELTest {

    @Test
    public void test1() {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Hello World'.concat('!')");
        String message = (String) expression.getValue();
        System.out.println(message);
    }

    @Test
    public void test2() {
        ExpressionParser parser = new SpelExpressionParser();

        // invokes getBytes()
        Expression exp = parser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp.getValue();
        System.out.println(bytes);
    }

}

package boot.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by huishen on 17/9/2.
 *
 */

@Component
public class LoginFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init loginFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        StringBuffer path2 = req.getRequestURL();

        if ("OPTIONS".equals(req.getMethod())
            || path.startsWith("/")
            || path.startsWith("/user/register")
            || path.startsWith("/login")
            || path.startsWith("/mytest")
            || path.startsWith("/index")
            || path.startsWith("/favicon.ico")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession();
        if (!StringUtils.isEmpty(session.getAttribute("userId"))) {
            chain.doFilter(request, response);
            return;
        }

        request.getRequestDispatcher("/login/error").forward(request,response);
    }

    @Override
    public void destroy() {
        logger.info("destroy loginFilter");
    }
}

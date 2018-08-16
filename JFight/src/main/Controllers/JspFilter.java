package main.Controllers;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
public class JspFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        req.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

package wx.filter;

import admin.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/index.html"})
public class UserFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String userPhone = UserUtil.getUserPhone(request.getSession());
        if(userPhone != null)
            chain.doFilter(req, resp);
        else
            response.sendRedirect("login.html");
    }

}

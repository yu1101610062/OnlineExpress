package admin.filter;

import admin.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/admin/index.html","/admin/views/*","/express/*","/courier/*","/user/*"})
public class AccessControlFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String userName = UserUtil.getUserName(request.getSession());
        if(userName != null)
            chain.doFilter(req, resp);
        else
            response.sendError(404,"很遗憾,权限不足");
    }

}

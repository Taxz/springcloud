package txz.study.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/6/14.
 */
@Component
public class ErrorExtFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 30;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        ZuulFilter filtertpe = (ZuulFilter) context.get("failed.filter");
        if (filtertpe != null && filtertpe.filterType().equals("post")) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        Throwable throable = context.getThrowable();
        context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        context.set("error.exception", throable.getCause());
        context.set("error.message","在post中出现异常");
        System.out.println("异常在这里了");
        return null;
    }
}

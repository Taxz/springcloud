package txz.study.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/6/14.
 */

@Component
public class ThrowExceptionFilter extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(ThrowExceptionFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        logger.info("will throw RuntimeException");
        RequestContext cxt = RequestContext.getCurrentContext();
       /* try {*/

            dosomething();
        /*} catch (Exception e) {
            cxt.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           // cxt.set("error.message", "异常。。。");
            cxt.set("error.exception",e);
        }*/
        return null;
    }

    private void dosomething() {
        throw new RuntimeException("就想抛个异常啊");
    }
}

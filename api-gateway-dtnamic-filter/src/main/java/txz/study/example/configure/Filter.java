package txz.study.example.configure;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by Administrator on 2018/6/14.
 */
@Component
public class Filter extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(Filter.class);


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        logger.info("here ....to ");
        System.out.println("------------------------------lalalalalalal---------------------------------------");
        return null;
    }
}


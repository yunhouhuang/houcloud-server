package com.houcloud.example.common.security.filter;


import com.houcloud.example.common.security.token.store.AuthContext;
import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * <p>
 * 请求响应过滤器（脱敏/加密/过滤）
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Component
public class ResponseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 清除线程中的用户信息，防止特殊情况下线程复用导致错误。
        AuthContext.clearContextUser();
        // 暂时不处理，直接返回
        filterChain.doFilter(request, response);
    }

}


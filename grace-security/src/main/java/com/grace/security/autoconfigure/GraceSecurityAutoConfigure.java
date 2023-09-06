package com.grace.security.autoconfigure;

import com.grace.security.token.TokenManagerDelegate;
import com.grace.security.token.impl.CachedJwtTokenManager;
import com.grace.security.token.impl.JwtTokenManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        TokenManagerDelegate.class,
        JwtTokenManager.class,
        CachedJwtTokenManager.class
})
public class GraceSecurityAutoConfigure {


}

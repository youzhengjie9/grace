package com.grace.client.misc;

import com.grace.common.dto.UserLoginDTO;
import com.grace.common.vo.TokenVO;

public interface LoginService {

    TokenVO login(UserLoginDTO userLoginDTO);


}

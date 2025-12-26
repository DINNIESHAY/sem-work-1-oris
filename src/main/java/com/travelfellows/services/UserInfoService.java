package com.travelfellows.services;

import com.travelfellows.models.UserInfo;
import com.travelfellows.repositories.impl.JdbcUserInfoRepository;

public class UserInfoService {

    private final JdbcUserInfoRepository userInfoRepository;

    public UserInfoService(JdbcUserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public boolean save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    public UserInfo findById(Long userId) {
        return userInfoRepository.findById(userId).orElse(null);
    }

    public boolean update(UserInfo userInfo) {
        return userInfoRepository.update(userInfo);
    }
}

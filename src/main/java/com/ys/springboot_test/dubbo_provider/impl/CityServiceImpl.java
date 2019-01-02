package com.ys.springboot_test.dubbo_provider.impl;

import com.ys.springboot_test.dubbo_provider.ICityService;
import com.ys.springboot_test.entity.City;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements ICityService {

    @Override
    public City getCity() {
        return new City("新昌","我的家乡，阿西吧");
    }
}

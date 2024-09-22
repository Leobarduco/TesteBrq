package com.testebrq.testeBrq.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class Cache {
	@Bean
	public Caffeine<Object, Object> caffeineConfig(){
		return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS);
	}
}
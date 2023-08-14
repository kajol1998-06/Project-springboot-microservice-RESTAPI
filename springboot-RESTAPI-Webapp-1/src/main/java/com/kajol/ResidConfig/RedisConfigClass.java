package com.kajol.ResidConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kajol.Reside.DataLoadClass;
import com.kajol.dao.ServicePrice;

@Configuration
@EnableWebSecurity
public class RedisConfigClass extends WebSecurityConfigurerAdapter{

	@Bean
	public JedisConnectionFactory jediscon()
	{
		JedisConnectionFactory jedis=new JedisConnectionFactory();
		return jedis;
	}
	
	@Bean
	public RedisTemplate<String,ServicePrice> redisTemplate()
	{
		RedisTemplate<String,ServicePrice> redistem=new RedisTemplate();
		redistem.setConnectionFactory(jediscon());
		// Use Jackson2JsonRedisSerializer for value serialization
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // Use StringRedisSerializer for key serialization
        redistem.setKeySerializer(new StringRedisSerializer());
        redistem.setValueSerializer(jackson2JsonRedisSerializer);

        redistem.afterPropertiesSet();
		return redistem;
	}
	@Bean
	public DataLoadClass createBean()
	{
		DataLoadClass dataload=new DataLoadClass(redisTemplate());
		return dataload;
		
	}
	
	@Autowired
	public MyUserDetailsService userdetails;
	
	@Autowired
	public void congif(AuthenticationManagerBuilder build) throws Exception
	{
		build.userDetailsService(userdetails).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
	        .antMatchers("/h2-console/**").permitAll() // Allow access to H2 Console without authentication
	        .anyRequest().authenticated()
	        .and()
			    .formLogin().and()
			    .csrf().ignoringAntMatchers("/h2-console/**") // Disable CSRF for H2 Console
			    .and()
			    .headers().frameOptions().sameOrigin(); 
	}
	
}

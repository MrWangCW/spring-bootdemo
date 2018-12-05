package com.wang;

import com.wang.util.propertiesutil.PropertiesListener;
import com.wang.util.propertiesutil.PropertiesUtil;
import com.wang.util.propertiesutil.RedisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 启动类
 *
 * @author wang
 * @date 2018/6/30
 */
@Controller
@RequestMapping("boot")
@SpringBootApplication
public class SpringBootdemoApplication extends SpringBootServletInitializer{

	public SpringBootdemoApplication() {
		super();
		super.setRegisterErrorPageFilter(false);
	}

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(SpringBootdemoApplication.class);
		// 第四种方式：注册监听器
		application.addListeners(new PropertiesListener("connect-redis.properties"));
		application.addListeners(new PropertiesListener("application.properties"));
		//关闭Banner
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		/*//关闭Banner
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBootdemoApplication.class);
		//修改Banner的模式为OFF
		builder.bannerMode(Banner.Mode.OFF).run(args);*/
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootdemoApplication.class);
	}

	@Value(value = "${spring.redis.host}")
	private String host;
	@Value(value = "${spring.redis.port}")
	private String port;

	@Autowired
	private RedisDto redisDto;

	@RequestMapping(value = "/test",produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String test(){
		//测试111
		//测试222
		//测试啊啊啊啊啊
		if(true){
			System.out.println("111111");
		}
		return "host:"+host+"port："+port+"host："+redisDto.getHost()+"port:"+redisDto.getPort();
	}


}

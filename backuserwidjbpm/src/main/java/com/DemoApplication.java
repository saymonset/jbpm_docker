package com;

import com.dao.UserRole;
import com.dao.User;
import com.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.inject.Inject;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages={"com"})
public class DemoApplication implements CommandLineRunner, ApplicationRunner {
	@Inject
	private UserRepository userRepository;
	/**public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}**/
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}



	public void run(ApplicationArguments applicationArguments) throws Exception {
		List<User> users = (List<User>)userRepository.findAll();

		if (users !=null && users.isEmpty()){
			User user = new User();
			user.setLogin("sami");
			user.setFirstName("sami");
			user.setLastName("Sami");
			user.setEmail("sami@hotmail.com");
			user.setStatus(true);
			UserRole role = new UserRole();
			role.setUser(user);
			role.setName("maturin");
			user.getRoles().add(role);
			userRepository.save(user);
		}
		for (User u:users){
			System.out.println("u.getLoginName() = " + u.getFirstName());
		}

	}


	public void run(String... strings) throws Exception {

	}
}

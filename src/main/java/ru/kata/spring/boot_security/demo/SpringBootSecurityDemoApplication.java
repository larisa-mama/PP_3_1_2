package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

}


/*
1. таблица roles в mySQL
2. таблица users
3. WebSecurityConfig по задаче с хабра, в MvcConfig три ссылки
4. Dao для User из прошлой задачи. добавила один метод поиска по email
5. UserDaoImpl (@Repository) тоже из прошлой задачи. merge может изменять текущего юзера и без id. Метод добавления изменился из-за роли
6. RoleDao c одним методом поиска роли по id
7. Сущность Role c таблицы (связана с таблицей юзеров )
8. Сущность User с таблицы (ManyToMany). Получить юзера по имени сменила на сравнение по email, как в примере с хабра
9. Service для юзера имеет те же методы







 */
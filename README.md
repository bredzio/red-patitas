# Educación Egg

## Objective:

Develop a website for an ONG whose objective is to find lost pets.

![screen-recording](https://user-images.githubusercontent.com/85000317/153296205-f6c7d2ae-2833-4cfd-9efe-56990abd6aa6.gif)


## Technologies
<p align="left"><a href="https://www.java.com" target="_blank"><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/></a> 
<a href="https://developer.mozilla.org/es/docs/Web/JavaScript"><img src="https://raw.githubusercontent.com/get-icon/geticon/master/icons/javascript.svg"  width="40px" height="40"/></a>
<a href="https://spring.io/" target="_blank"><img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/></a>
<a href="https://www.w3.org/html/" target="_blank"><img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/html/html.png"  width="40px" height="40"/></a>
<a href="https://www.w3schools.com/css/" target="_blank"> <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/css/css.png"  width="40px" height="40"/></a>
<a href="https://getbootstrap.com" target="_blank"><img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/bootstrap/bootstrap.png"  width="40px" height="40"/></a> 
<a href="https://git-scm.com/"><img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/git/git.png"  width="40px" height="40"/></a>
<a href="https://github.com/"><img src="https://raw.githubusercontent.com/github/explore/78df643247d429f6cc873026c0622819ad797942/topics/github/github.png"  width="40px" height="40"/>
<a href="https://code.visualstudio.com/" target="_blank"><img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/visual-studio-code/visual-studio-code.png"  width="40px"height="40"/> </a>
<img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/terminal/terminal.png"  width="40px" height="40"/><a href="https://www.mysql.com/" target="_blank"><img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/mysql/mysql.png" alt="java" width="40" height="40"/></a> </p>

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Mysql - 8.x.x

## Dependencies:
- ✔️ Spring Data JPA 
- ✔️ MySQL DRIVER
- ✔️ Spring Web
- ✔️ Springboot Security
- ✔️ Springboot Starter Validation
- ✔️ Springboot Devtools
- ✔️ Springboot Thymeleaf
- ✔️ Lombok
- ✔️ Mail Sender
- ✔️ Amazon Web Service


## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/bredzio/red-patitas.git
```

**2. Create Mysql database**
```bash
create database huellapp
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Change email username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.mail.username` , `spring.mail.password` , `spring.mail.port` , `spring.mail.host` as per your mail configuration

**5. Change AWS credentials as per your installation**

+ change `amazonProperties.access-key` , `amazonProperties.secret-key` , `amazonProperties.region` , `amazonProperties.bucketName`, `amazonProperties.endpointUrl` as per your Amazon Web Service configuration


**6. Build and run the app using maven**


```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

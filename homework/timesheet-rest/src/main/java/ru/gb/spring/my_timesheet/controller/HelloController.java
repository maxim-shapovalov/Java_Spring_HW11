package ru.gb.spring.my_timesheet.controller;

import org.springframework.web.bind.annotation.*;

//@Controller
//@ResponseBody
@RestController // эта аннотация имеет в себе аннотацию @ResponseBody
public class HelloController {

    //     GET http://localhost:8080/hello
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
//    @ResponseBody    // при использовании @Controller необходимо указывать эту аннотацию, чтобы сервер мог присылать ответ.
//    Ее можно указать над классом, тогда она будет для всех методов
    public String helloPage(){
        return "Hello world!";
    }

    @GetMapping("/home") // аннотация равносильна той, что выше
    public String homePage(){
        return "Home page";
    }


    //    // GET http://localhost:8080/hello?username=Igor // В HTTP после знака ? можно передавать какие то
//    параметры(query параметры), здесь username
    @GetMapping("/hellouser")
    public String helloPage(@RequestParam String username) { // если имя переменной в методе должно быть другим,
        // можно указать @RequestParam("username") String login (например), как в примере ниже
        return "<h1>Hello, " + username + "!</h1>";
    }

    // GET http://localhost:8080/hello/igor
    // GET http://localhost:8080/hello/alex
    @GetMapping("/hellouser/{username}")
    public String helloPagePathVariable(@PathVariable("username") String user) {
        return "<h1>Hello, " + user + "!</h1>";
    }

}

package spring.security.study.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/check")
    public String isServerUp(){
        return "server is running";
    }

    @GetMapping("/user")
    public String getUser(Authentication authentication){
        return "User method called successfully"+" Username : "+authentication.getName() + "- Authority : "+authentication.getAuthorities();
    }

    @GetMapping("/admin")
    public String getAdmin(Authentication authentication){
        return "Admin method called successfully" +" Username : "+authentication.getName() + "- Authority : "+authentication.getAuthorities();
    }

}

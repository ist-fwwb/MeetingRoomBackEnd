package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Util.PageRequestFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Validated
@RequestMapping("/user")
@RestController
@Api(value="User", description = "UserController")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PageRequestFactory pageRequestFactory;
    @GetMapping("/")
    @ApiOperation(value="get all of the user's detail information")

    public Page<User> showAll(@RequestParam(name="pageNumber") int pageNumber,
                              @RequestParam(name="pageSize") int pageSize){
        PageRequest pageRequest = pageRequestFactory.create(pageNumber, pageSize);
        return userService.showAll(pageRequest);
    }

    @PostMapping("/")
    @ApiOperation(value="registor a user")
    public User register(@RequestParam(name = "enterpriseId") String enterpriseId,
                         @RequestParam(name = "phone") String phone, @RequestParam(name ="password") String password,
                         @RequestParam(name = "faceFile") MultipartFile faceFile,
                         @RequestParam(name = "fetureFile") MultipartFile featureFile,
                         @RequestParam(name = "name") String name) throws Exception{
        return userService.register(enterpriseId, phone, password, faceFile.getBytes(), featureFile.getBytes(), name);
    }

    @GetMapping("/{id}")
    @ApiOperation(value="get a specified user's detail informatipon")
    public User showOne(@PathVariable(name="id")String id){
        return userService.showOne(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value="modify a specified user's status")
    public User modify(@PathVariable(name="id")String id, @RequestParam(name="type") Type type){
        return userService.modifyType(id, type);
    }

    @PostMapping("/login")
    @ApiOperation(value="log in the app with phone and password")
    public User login(@RequestParam(name="phone") String phone, @RequestParam(name="password") String password,
                      HttpServletRequest request){
        User user = userService.login(phone, password);
        if (user == null) return null;
        else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return user;
        }
    }

}

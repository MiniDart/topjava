package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "email", required = false) String email,
                               @RequestParam(value = "password", required = false) String password,
                               @RequestParam(value = "enabled", required = false) String enabled) {
        if (enabled!=null){
            User user=get(id);
            user.setEnabled("true".equals(enabled));
            super.update(user,id);
            return;
        }
        User user = new User(id, name, email, password, Role.ROLE_USER);
        if (user.isNew()) {
            super.create(user);
        }
        else {
        }
    }
}

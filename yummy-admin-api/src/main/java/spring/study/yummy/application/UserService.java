package spring.study.yummy.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.study.yummy.domain.User;
import spring.study.yummy.domain.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        user.setLevel(1L);
        return userRepository.save(user);
    }

    public User updateUser(Long id, String name, String email, Long level) {
        User user = userRepository.findById(id).orElse(null);

        user.updateInformation(name, email, level);

        return user;
    }

    public User deactivateUser(Long id) {
        User user = userRepository.findById(id).orElse(null);

        user.deactivate();

        return user;
    }
}

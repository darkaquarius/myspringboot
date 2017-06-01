package boot.service;

import boot.domain.User;

/**
 * Created by huishen on 16/10/8.
 */
public interface UserService {

    User addUser(User user);

    User updateUser(User user);

    User getUserById(int id);

}

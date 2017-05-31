package boot.service;

import boot.domain.User;

/**
 * Created by huishen on 16/10/8.
 */
public interface UserService {

    int addUser(User user);

    int updateUser(User user);

    User getUserById(int id);

}

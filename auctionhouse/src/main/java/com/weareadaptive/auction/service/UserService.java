package com.weareadaptive.auction.service;

import com.weareadaptive.auction.model.User;
import com.weareadaptive.auction.repository.UserRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(
      String username,
      String password,
      String firstName,
      String lastName,
      String organisation) {

    User user = new User();

    user.setUsername(username);
    user.setPassword(password);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setOrganisation(organisation);

    return userRepository.save(user);
  }

  public User get(int id) {
    return userRepository.get(id);
  }

  public Stream<User> all() {
    return userRepository.all();
  }

  public User update(
      int id,
      String firstName,
      String lastName,
      String organisation) {

    User user = userRepository.get(id);

    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setOrganisation(organisation);

    return userRepository.save(user);
  }

  public void block(int id) {
    userRepository.get(id).block();
  }

  public void unblock(int id) {
    userRepository.get(id).unblock();
  }

  public Optional<User> validateUsernamePassword(String username, String password) {
    return userRepository.validateUsernamePassword(username, password);
  }
}

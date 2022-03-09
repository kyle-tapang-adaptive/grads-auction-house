package com.weareadaptive.auction.service;

import com.weareadaptive.auction.exception.ItemDoesNotExistException;
import com.weareadaptive.auction.model.BusinessException;
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

    userRepository.getByUsername(username)
        .ifPresent(u -> {
          throw new BusinessException("Item already exists");
        });

    User user = new User();

    user.setUsername(username);
    user.setPassword(password);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setOrganisation(organisation);

    return userRepository.save(user);
  }

  public User get(int id) {
    return userRepository.get(id)
      .orElseThrow(() -> new ItemDoesNotExistException("User does not exist"));
  }

  public Stream<User> all() {
    return userRepository.all().stream();
  }

  public User update(
      int id,
      String firstName,
      String lastName,
      String organisation) {
    get(id); //Checks if user exists, else throws
    userRepository.update(id, firstName, lastName, organisation);
    return get(id);
  }

  public void block(int id) {
    get(id); //Checks if user exists, else throws
    userRepository.block(id);
  }

  public void unblock(int id) {
    get(id); //Checks if user exists, else throws
    userRepository.unblock(id);
  }

  public Optional<User> validateUsernamePassword(String username, String password) {
    return userRepository.validateUsernamePassword(username, password);
  }
}

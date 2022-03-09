package com.weareadaptive.auction.configuration;

import com.weareadaptive.auction.model.User;
import com.weareadaptive.auction.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInit {
  private final UserRepository userRepository;

  public ApplicationInit(UserRepository userRepository) {
    this.userRepository = userService;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void createInitData() {
    var admin = new User(
        userRepository.nextId(),
        "ADMIN",
        "adminpassword",
        "admin",
        "admin",
        "Adaptive",
        true);
    userRepository.add(admin);
  }

}

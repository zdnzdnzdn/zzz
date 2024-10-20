package com.example.testserverapp.service;

import com.example.testserverapp.entity.User;
import com.example.testserverapp.model.AppUserDetail;
import com.example.testserverapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = userRepository
      .findByUsernameOrEmployeeEmail(username, username)
      .orElseThrow(() ->
        new UsernameNotFoundException("Username or Email not found!!!")
      );

    return new AppUserDetail(user);
  }
}
//package com.eatwell.foodHQ.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class ApplicationUserService implements UserDetailsService {
//    private final ApplicationUserDao applicationUserDao;
//    @Autowired
//    public ApplicationUserService(ApplicationUserDao applicationUserDao) {
//        this.applicationUserDao = applicationUserDao;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return applicationUserDao.findByUserName(username)
//                .orElseThrow(()-> new UsernameNotFoundException("UserName not found " + username));
//
//    }
//}

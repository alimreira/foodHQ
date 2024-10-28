//package com.eatwell.foodHQ.auth;
//
//import com.eatwell.foodHQ.security.ApplicationUserPermission;
//import com.eatwell.foodHQ.security.ApplicationUserRole;
//import com.google.common.collect.Lists;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//@Repository("repo")
////@Repository("repo")
//public class FakeApplicationDaoUserService implements ApplicationUserDao{
//    @Bean
//    PasswordEncoder passwordEncoder () {
//        return new BCryptPasswordEncoder();
//    }
//    @Override
//    public Optional<ApplicationUser> findByUserName(String username) {
//        return getApplicationUsers().stream()
//                .filter((applicationUser) -> username.equals(applicationUser.getUsername()))
//                .findFirst();
//    }
//
//    private List<ApplicationUser> getApplicationUsers () {
//        List<ApplicationUser> applicationUsers = Lists.newArrayList(
//               new ApplicationUser(
//                       "Sarah Smith",
//                       passwordEncoder().encode("password"),
//                       ApplicationUserRole.CUSTOMER.getGrantedAuthorities(),
//                       true,
//                       true,
//                       true,
//                       true
//               ),
//                new ApplicationUser(
//                       "Linda Smith",
//                       passwordEncoder().encode("password"),
//                       ApplicationUserRole.ADMIN.getGrantedAuthorities(),
//                       true,
//                       true,
//                       true,
//                       true
//               ),
//                new ApplicationUser(
//                       "Tom Smith",
//                       passwordEncoder().encode("password"),
//                       ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
//                       true,
//                       true,
//                       true,
//                       true
//               )
//        );
//        return applicationUsers;
//    }
//}

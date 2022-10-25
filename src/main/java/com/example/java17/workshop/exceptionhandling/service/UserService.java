package com.example.java17.workshop.exceptionhandling.service;

import com.example.java17.workshop.exceptionhandling.controller.advice.error.GeneralError;
import com.example.java17.workshop.exceptionhandling.controller.advice.error.ReportError;
import com.example.java17.workshop.exceptionhandling.controller.advice.error.ServiceError;
import com.example.java17.workshop.exceptionhandling.controller.advice.exception.DatabaseAccessException;
import com.example.java17.workshop.exceptionhandling.controller.advice.exception.UserNotFoundInDbException;
import com.example.java17.workshop.exceptionhandling.domain.User;
import com.example.java17.workshop.exceptionhandling.repository.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Either<GeneralError, User> getUserV2(String userId) {
        return Try.of(() -> userRepository.getUser(userId))
                .toEither()
                .map(Option::ofOptional)
                .<GeneralError>mapLeft(ServiceError.DatabaseError::new)
                .flatMap(optionalUser -> optionalUser.toEither(new ReportError.ReportForUserNotFoundError(userId)));
    }

    public User getUserV1(String userId) {
        Optional<User> optionalUser;
        try {
            optionalUser = userRepository.getUser(userId);
        } catch (Exception e) {
            throw new DatabaseAccessException(e);
        }
        return optionalUser.orElseThrow(() -> new UserNotFoundInDbException(userId));
    }


}
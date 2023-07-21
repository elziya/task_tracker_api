package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.request.SignUpForm;
import ru.kpfu.itis.dto.response.AccountResponse;

public interface SignUpService {

    AccountResponse signUp(SignUpForm signUpForm);

    Boolean confirmEmail(String code);
}

package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.kpfu.itis.controllers.ConfirmController;
import ru.kpfu.itis.dto.request.SignUpForm;
import ru.kpfu.itis.dto.response.AccountResponse;
import ru.kpfu.itis.exceptions.AccountIllegalStateException;
import ru.kpfu.itis.exceptions.DuplicateEmailException;
import ru.kpfu.itis.exceptions.IllegalConfirmCodeException;
import ru.kpfu.itis.models.Account;
import ru.kpfu.itis.repositories.AccountRepository;
import ru.kpfu.itis.services.SignUpService;
import ru.kpfu.itis.utils.EmailUtil;

import static ru.kpfu.itis.dto.response.AccountResponse.from;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailUtil emailUtil;

    @Transactional
    @Override
    public AccountResponse signUp(SignUpForm signUpForm) {

        if (accountRepository.findByEmail(signUpForm.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        Account account = Account.builder()
                .firstName(signUpForm.getFirstName())
                .lastName(signUpForm.getLastName())
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .role(Account.Role.USER)
                .state(Account.State.NOT_CONFIRMED)
                .confirmCode(UUID.randomUUID())
                .build();

        Map<String, Object> data = new HashMap<>();
        data.put("username", account.getFirstName() + " " + account.getLastName());
        data.put("confirmLink", MvcUriComponentsBuilder.fromController(ConfirmController.class).build().toString() + "/"
                + account.getConfirmCode());

        emailUtil.sendMail(account.getEmail(), "Tracker Email Confirmation", "confirm_mail", data);

        return from(accountRepository.save(account));
    }

    @Override
    public Boolean confirmEmail(String code) {
        UUID confirmCode;
        try {
            confirmCode = UUID.fromString(code);
        }
        catch (IllegalArgumentException e){
            throw new IllegalConfirmCodeException();
        }

        Account account = accountRepository.findByConfirmCode(confirmCode)
                .orElseThrow(IllegalConfirmCodeException::new);

        if (!account.getState().equals(Account.State.CONFIRMED)) {
            account.setState(Account.State.CONFIRMED);
            accountRepository.save(account);
        } else {
            throw new AccountIllegalStateException();
        }
        return true;
    }
}

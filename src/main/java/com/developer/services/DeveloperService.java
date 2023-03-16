package com.developer.services;

import com.developer.enums.DevStatus;
import com.developer.enums.Role;
import com.developer.models.Developer;
import com.developer.repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.developerRepository = developerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Developer getDeveloperById(Long id) {
        return developerRepository.findById(id).orElse(null);
    }

    public Long deleteDeveloperById(Long id) {
        Developer developer = developerRepository.findById(id).orElse(null);
        if (developer == null)
            return null;

        developer.setStatus(DevStatus.DELETED);
        return id;
    }

    public Long updateDeveloper(Long id, Developer developer) {
        Developer existingDeveloper = developerRepository.findById(id).orElse(null);
        if (existingDeveloper == null)
            return null;

        existingDeveloper.setEmail(developer.getEmail());
        existingDeveloper.setPassword(developer.getPassword());
        existingDeveloper.setFullName(developer.getFullName());
        existingDeveloper.setPosition(developer.getPosition());
        return developerRepository.save(existingDeveloper).getId();
    }

    public Optional<Developer> findByEmail(String email) {
        return developerRepository.findByEmail(email);
    }

    public List<Developer> findByTeamId(Long id) {
        return developerRepository.findByTeamIdAndStatus(id, DevStatus.ACTIVE);
    }

    public List<String> activateAccount(String email, String token) {
        Optional<Developer> developer = developerRepository.findByEmail(email);

        if (developer.isEmpty())
            return Collections.singletonList("Пользователь с таким email не найден");

        if (!developer.get().getActivationToken().equals(token))
            return Collections.singletonList("Неправильный токен активации");

        developer.get().setStatus(DevStatus.ACTIVE);
        developerRepository.save(developer.get());

        return Collections.singletonList("Аккаунт успешно активирован!");
    }

    public void registerUser(Developer developer) throws MessagingException {
        developer.setActivationToken(UUID.randomUUID().toString());
        developer.setPassword(passwordEncoder.encode(developer.getPassword()));
        developer.setStatus(DevStatus.PENDING);
        developer.setRole(Role.ROLE_USER);
        developerRepository.save(developer);

        String activationLink = "http://localhost:8080/activate?email=" + developer.getEmail() +
                "&token=" + developer.getActivationToken();
        emailService.sendActivationEmail(developer.getEmail(), activationLink);
    }

    public ResponseEntity<?> resetPassword(String email) {
        Optional<Developer> developer = developerRepository.findByEmail(email);
        if (developer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String resetToken = UUID.randomUUID().toString();
        developer.get().setResetToken(resetToken);
        developer.get().setResetTokenExpireTime(LocalDateTime.now().plusMinutes(30)); // установка срока действия токена
        developerRepository.save(developer.get());

        String resetUrl = "http://localhost:8080/api/password/reset/" + resetToken;
        String emailText = "Для сброса пароля перейдите по ссылке: " + resetUrl;

        emailService.sendSimpleMessage(email, "Сброс пароля", emailText);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> saveNewPassword(String resetToken, String newPassword) {
        Developer developer = developerRepository.findByResetToken(resetToken);
        if (developer == null || developer.getResetTokenExpireTime().isBefore(LocalDateTime.now()))
            return ResponseEntity.badRequest().build();

        developer.setPassword(passwordEncoder.encode(newPassword));
        developer.setResetToken(null);
        developer.setResetTokenExpireTime(null);
        developerRepository.save(developer);

        return ResponseEntity.ok().build();
    }

}

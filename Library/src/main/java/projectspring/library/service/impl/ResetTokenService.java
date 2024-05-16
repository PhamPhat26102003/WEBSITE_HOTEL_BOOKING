package projectspring.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspring.library.model.ResetToken;
import projectspring.library.repository.IResetTokenRepository;
import projectspring.library.service.IResetTokenService;

@Service
public class ResetTokenService implements IResetTokenService {
    @Autowired
    private IResetTokenRepository resetTokenRepository;
    @Override
    public ResetToken findByToken(String token) {
        return resetTokenRepository.findByToken(token);
    }
}

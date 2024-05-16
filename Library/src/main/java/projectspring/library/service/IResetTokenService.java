package projectspring.library.service;

import projectspring.library.model.ResetToken;

public interface IResetTokenService {
    ResetToken findByToken(String token);
}

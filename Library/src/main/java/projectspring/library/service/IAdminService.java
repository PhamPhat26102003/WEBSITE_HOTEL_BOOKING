package projectspring.library.service;

import projectspring.library.dto.AdminDto;
import projectspring.library.model.Admin;

import java.util.List;

public interface IAdminService {
    Admin findByUsername(String username);
    Admin register(AdminDto adminDto);
    String sendEmail(Admin admin);
    String generateResetToken(Admin admin);
}

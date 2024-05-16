package projectspring.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import projectspring.library.dto.AdminDto;
import projectspring.library.model.Admin;
import projectspring.library.model.ResetToken;
import projectspring.library.repository.IAdminRepository;
import projectspring.library.repository.IResetTokenRepository;
import projectspring.library.repository.IRoleRepository;
import projectspring.library.service.IAdminService;

import java.util.Arrays;
import java.util.UUID;

@Service
public class AdminService implements IAdminService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private IAdminRepository adminRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IResetTokenRepository resetTokenRepository;

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin register(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public String sendEmail(Admin admin) {
        try{
            String resetLink = generateResetToken(admin);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(admin.getUsername());
            msg.setSubject("My name is Phat");
            msg.setText("You gonna change password \n\n" + "Please  click on this link: " + resetLink + ". \n\n"
                            + "Regards \n" + "ABC");
            mailSender.send(msg);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String generateResetToken(Admin admin) {
        UUID uuid = UUID.randomUUID();
        ResetToken resetToken = new ResetToken();
        resetToken.setAdmin(admin);
        resetToken.setToken(uuid.toString());
        resetToken.setAdmin(admin);
        ResetToken token = resetTokenRepository.save(resetToken);
        if(token != null){
            String url = "http://localhost:8098/admin/reset-password";
            return url + "/" + resetToken.getToken();
        }
        return "";
    }
}

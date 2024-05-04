package projectspring.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspring.library.dto.AdminDto;
import projectspring.library.model.Admin;
import projectspring.library.repository.IAdminRepository;
import projectspring.library.repository.IRoleRepository;
import projectspring.library.service.IAdminService;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private IAdminRepository adminRepository;
    @Autowired
    private IRoleRepository roleRepository;
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
}

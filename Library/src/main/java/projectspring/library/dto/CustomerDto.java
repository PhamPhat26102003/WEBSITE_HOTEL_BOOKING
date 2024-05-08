package projectspring.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import projectspring.library.model.City;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String repeatPass;
    private String phone;
    private String address;
    private String country;
    private City city;
}

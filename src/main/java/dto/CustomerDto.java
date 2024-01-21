package dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDto {
    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String nic;
    private String address;
    private String city;
    private String province;
    private List<String> phone;
    private String email;
    private String renting;
    private String createdBy;
    private String updatedBy;
}

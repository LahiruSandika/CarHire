package util;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerReport {
    private String id;
    private String name;
    private String nic;
    private String address;
    private String phone;
    private String email;
    private String renting;
}

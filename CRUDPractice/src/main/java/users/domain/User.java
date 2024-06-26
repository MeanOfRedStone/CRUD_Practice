package users.domain;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User {
    private Long userId;
    private String id;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String addressDetail;
    private int agreement;
    private int phoneContact;
    private int emailContact;

    @Builder
    public User(Long userId, String id, String password, String name, String email, String phone, String address, String addressDetail, int agreement, int phoneContact, int emailContact) {
        this.userId = userId;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.addressDetail = addressDetail;
        this.agreement = agreement;
        this.phoneContact = phoneContact;
        this.emailContact = emailContact;
    }
}

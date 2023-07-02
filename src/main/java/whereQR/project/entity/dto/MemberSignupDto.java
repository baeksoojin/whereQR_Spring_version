package whereQR.project.entity.dto;

import lombok.Data;
import org.apache.tomcat.jni.Address;
import whereQR.project.entity.PhoneNumber;
import whereQR.project.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberSignupDto {

    private String username;
    private int age;
    private Address address;
    private PhoneNumber phoneNumber;
    private List<String> roles;

    private String password;
    private String email;

    public MemberSignupDto(){

    }

    public MemberSignupDto(String username, int age, String email, List<String> roles){
            this.username = username;
            this.age = age;
            this.email = email;
            this.roles = roles;
    }
}

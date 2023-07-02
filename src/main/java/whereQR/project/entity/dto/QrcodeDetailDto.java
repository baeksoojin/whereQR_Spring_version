package whereQR.project.entity.dto;

import lombok.Data;
import whereQR.project.entity.Address;
import whereQR.project.entity.PhoneNumber;
import whereQR.project.entity.Qrcode;

import javax.persistence.Embedded;

@Data
public class QrcodeDetailDto {

    private String title;
    private String memo;

    @Embedded
    Address address;
    @Embedded
    PhoneNumber phoneNumber;

    public QrcodeDetailDto(){

    }

    public QrcodeDetailDto(Qrcode qrcode){
        this.title = qrcode.getTitle();
        this.memo = qrcode.getMemo();
        this.address = qrcode.getAddress();
        this.phoneNumber = qrcode.getPhoneNumber();
    }
}
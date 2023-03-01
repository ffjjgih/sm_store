package fpoly.edu.datn.vibee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill")
public class Bill {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CREATED_DATE")
    private String createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private String modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name="PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name="STATUS")
    private String status;

    //địa chỉ người mua hàng
    @Column(name= "RECEIVING_ADDRESS")
    private String receivingAddress;

    //số điện thoại người nhận hàng
    @Column(name= "RECEIVING_PHONE")
    private String receivingPhone;

    //tên người nhận hàng
    @Column(name= "RECEIVING_NAME")
    private String receivingName;

    //email người nhận hàng
    @Column(name= "RECEIVING_EMAIL")
    private String receivingEmail;

    @Column(name= "TAX")
    private BigDecimal tax;

    //tiền khuyến mãi
    @Column(name= "PROMOTION")
    private BigDecimal promotion;
}

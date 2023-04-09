package fpoly.edu.datn.vibee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name="PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name="STATUS")
    private String status;

    @Column(name= "DESCRIPTION")
    private String description;

    @Column(name= "FILE_ID")
    private int fileId;

    //phương thức nhận hàng
    @Column(name= "RECEIVING_METHOD")
    private String receivingMethod;

    //phương thức giao dịch
    @Column(name= "TRANSACTION_METHOD")
    private String transactionMethod;
    @Column(name= "TAX")
    private BigDecimal tax;

    //tiền khuyến mãi
    @Column(name= "PROMOTION")
    private BigDecimal promotion;
}

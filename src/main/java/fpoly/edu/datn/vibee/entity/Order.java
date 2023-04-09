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
@Table(name = "order")
public class Order {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    //tổng tiền
    @Column(name = "PRICE")
    private BigDecimal price;

    //thời gian đặt hàng
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    //thông tin người mua hàng
    @Column(name = "CREATED_BY")
    private String createdBy;

    //thời gian duyệt
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    //thông tin người duyệt
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    //trạng thái đơn hàng
    @Column(name= "STATUS")
    private String status;

    //địa chỉ người nhận hàng
    @Column(name= "RECEIVING_ADDRESS")
    private String receivingAddress;

    //số điện thoại người nhận hàng
    @Column(name= "RECEIVING_PHONE")
    private String receivingPhone;

    //tên người nhận hàng
    @Column(name= "RECEIVING_NAME")
    private String receivingName;

    //ghi chú
    @Column(name= "RECEIVING_NOTE")
    private String receivingNote;

    //chữ ký thanh toán
    @Column(name= "PAYMENT_SIGNATURE")
    private String paymentSignature;

    //tiền thuế
    @Column(name= "TAX")
    private BigDecimal tax;

    //tiền khuyến mãi
    @Column(name= "PROMOTION")
    private BigDecimal promotion;

    //thông tin người giao hàng
    @Column(name="SHIPPER_ID")
    private int shipperId;

    @Column(name="TRANSPORT_COMPANY_ID")
    private int transportCompanyId;

    @Column(name="BILL_ID")
    private int billId;
}

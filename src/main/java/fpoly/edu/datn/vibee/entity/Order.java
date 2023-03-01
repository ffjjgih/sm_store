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
@Table(name = "order")
public class Order {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    //tổng tiền
    @Column(name = "PRICE")
    private int price;

    //thời gian đặt hàng
    @Column(name = "CREATED_DATE")
    private String createdDate;

    //thông tin người mua hàng
    @Column(name = "CREATED_BY")
    private String createdBy;

    //thời gian duyệt
    @Column(name = "MODIFIED_DATE")
    private String modifiedDate;

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

    //email người nhận hàng
    @Column(name= "RECEIVING_EMAIL")
    private String receivingEmail;

    //ghi chú
    @Column(name= "RECEIVING_NOTE")
    private String receivingNote;

    //phương thức thanh toán
    @Column(name= "PAYMENT_METHOD")
    private String paymentMethod;

    //trạng thái thanh toán
    @Column(name= "PAYMENT_STATUS")
    private String paymentStatus;

    //mã thanh toán
    @Column(name= "PAYMENT_ID")
    private String paymentId;

    //thời gian thanh toán
    @Column(name= "PAYMENT_DATE")
    private String paymentDate;

    //số tiền thanh toán
    @Column(name= "PAYMENT_AMOUNT")
    private String paymentAmount;

    //đơn vị tiền tệ
    @Column(name= "PAYMENT_CURRENCY")
    private String paymentCurrency;

    //mô tả thanh toán
    @Column(name= "PAYMENT_DESCRIPTION")
    private String paymentDescription;

    //chữ ký thanh toán
    @Column(name= "PAYMENT_SIGNATURE")
    private String paymentSignature;

    //số tài khoản ngân hàng
    @Column(name= "PAYMENT_BANK_CODE")
    private String paymentBankCode;

    //tên ngân hàng
    @Column(name= "PAYMENT_BANK_NAME")
    private String paymentBankName;

    //phí thanh toán
    @Column(name= "PAYMENT_FEE")
    private String paymentFee;

    //tiền thuế
    @Column(name= "TAX")
    private BigDecimal tax;

    //tiền khuyến mãi
    @Column(name= "PROMOTION")
    private BigDecimal promotion;

    //thông tin người giao hàng
    @Column(name="SHIPPER_ID")
    private int shipperId;
}

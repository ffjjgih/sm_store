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
@Table(name = "warehouse_info")
public class WarehouseInfo {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name= "PRODUCT_VERSION_ID")
    private int productVersionId;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "SUM_IN_PRICE")
    private BigDecimal sumInPrice;

    @Column(name = "SUM_OUT_PRICE")
    private BigDecimal sumOutPrice;

    @Column(name = "SUM_IN_AMOUNT")
    private int sumInAmount;

    @Column(name = "SUM_OUT_AMOUNT")
    private int sumOutAmount;

    @Column(name = "NUMBER_OF_IN")
    private int numberOfIn;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
}

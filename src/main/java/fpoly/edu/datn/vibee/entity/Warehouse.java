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
@Table(name = "warehouse")
public class Warehouse {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name="WAREHOUSE_INFO_ID")
    private int warehouseInfoId;

    @Column(name = "IN_AMOUNT")
    private int inAmount;

    @Column(name = "OUT_AMOUNT")
    private int outAmount;

    @Column(name = "IN_PRICE")
    private BigDecimal inPrice;

    @Column(name = "OUT_PRICE")
    private BigDecimal outPrice;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name="ENTRY_OF_NUMBER")
    private int entryOfNumber;


}

package fpoly.edu.datn.vibee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rate")
public class Rate {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "PRODUCT_ID")
    private int productId;

    @Column(name = "RATE")
    private int rate;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "CREATED_DATE")
    private String createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private String modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
}

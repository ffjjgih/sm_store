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
@Table(name = "shipper")
public class Shipper {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CREATED_DATE")
    private String createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private String modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "TRANSPORT_COMPANY_ID")
    private String transportCompanyId;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NUMBER_OF_DELIVERY")
    private int numberOfDelivery;
}

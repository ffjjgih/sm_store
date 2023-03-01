package fpoly.edu.datn.vibee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    //tên sản phẩm
    @Column(name = "NAME")
    private String name;

    //ảnh đại diện
    @Column(name = "IMAGE_PRIMARY")
    private int imagePrimary;

    //trạng thái
    @Column(name = "STATUS")
    private String status;

    //đánh giá sao
    @Column(name = "RATE_ID")
    private int rateId;

    //ngày tạo
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    //người tạo
    @Column(name = "CREATED_BY")
    private String createdBy;

    //ngày sửa
    @Column(name = "MODIFIED_DATE")
    private Date updatedDate;

    //người sửa
    @Column(name = "MODIFIED_BY")
    private String updatedBy;

    //mô tả
    @Column(name = "DESCRIPTION")
    private String description;

    //ảnh phụ
    @Column(name = "PRODUCT_IMAGE")
    private String productImages;

    //phiên bản sản phẩm
    @Column(name = "PRODUCT_SERIES_ID")
    private int productSeriesId;
}

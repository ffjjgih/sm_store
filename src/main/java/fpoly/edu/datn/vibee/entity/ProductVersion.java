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
@Table(name = "product_version")
public class ProductVersion {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    //bài viết sản phẩm
    @Column(name = "PRODUCT_INTRO_ID")
    private int productIntroId;

    //thuộc tính sản phẩm
    @Column(name = "PRODUCT_PROPERTY_ID")
    private int productPropertyId;
}

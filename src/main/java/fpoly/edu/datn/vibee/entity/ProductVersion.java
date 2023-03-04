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

    //ram
    @Column(name = "RAM")
    private Double ram;

    @Column(name = "ROM")
    private Double rom;

    @Column(name = "CPU")
    private String cpu;

    @Column(name = "GPU")
    private String gpu;

    @Column(name = "SCREEN_SIZE")
    private String screenSize;

    @Column(name = "PIN")
    private String pin;

    @Column(name = "CAMERA")
    private String camera;

    @Column(name = "COLOR")
    private String color;
}

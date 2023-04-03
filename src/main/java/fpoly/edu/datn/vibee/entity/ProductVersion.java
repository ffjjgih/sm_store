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
@Table(name = "product_version")
public class ProductVersion {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    //bài viết sản phẩm
    @Column(name = "PRODUCT_ID")
    private int productId;

    //thuộc tính sản phẩm

    //ram
    @Column(name = "RAM")
    private String ram;

    @Column(name = "ROM")
    private String rom;

    @Column(name = "CPU")
    private String cpu;

    @Column(name = "SCREEN_SIZE")
    private double screenSize;

    @Column(name = "PIN")
    private String pin;

    @Column(name = "IMAGE")
    private int image;

    @Column(name = "COLOR")
    private String color;
}

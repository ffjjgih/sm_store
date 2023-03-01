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
@Table(name = "product_property")
public class ProductProperty {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "SCREEN")
    private String screen;

    @Column(name = "CPU")
    private String cpu;

    @Column(name = "RAM")
    private Double ram;

    @Column(name = "ROM")
    private Double rom;

    @Column(name = "CAMERA")
    private String camera;

    @Column(name = "PIN")
    private String pin;

    @Column(name = "DESIGN")
    private String design;

    @Column(name = "CONNECT")
    private String connect;

    @Column(name = "OPERATING_SYSTEM")
    private String operatingSystem;

    @Column(name = "INFORMATION_OTHER")
    private String informationOther;
}

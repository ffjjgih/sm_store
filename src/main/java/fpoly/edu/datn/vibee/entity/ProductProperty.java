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


    @Column(name = "CPU")
    private String cpu;

    @Column(name = "RAM")
    private String ram;

    @Column(name = "ROM")
    private String rom;

    @Column(name = "OPERATING_SYSTEM")
    private String operatingSystem;

    @Column(name="SCREEN_SIZE")
    private double screenSize;
    @Column(name="SCREEN_RESOLUTION")
    private String screenResolution;

    @Column(name="SCREEN_TECHNOLOGY")
    private String screenTechnology;

    @Column(name="SWEEP_FREQUENCY")
    private String sweepFrequency;

    @Column(name="CAMERA_FRONT")
    private String cameraFront;

    @Column(name="CAMERA_REAR")
    private String cameraRear;

    @Column(name="PIN_CAPACITY")
    private String pinCapacity;

    @Column(name="CHARGING_SPEED")
    private String chargingSpeed;

    @Column(name="CHARGING_TYPE")
    private String chargingType;

    @Column(name="SIM")
    private String sim;

    @Column(name="IS_Jack")
    private boolean isJack;

    @Column(name="WEIGHT")
    private String weight;

    @Column(name="FRAME_MATERIAL")
    private String frameMaterial;

    @Column(name="BACK_MATERIAL")
    private String backMaterial;
}

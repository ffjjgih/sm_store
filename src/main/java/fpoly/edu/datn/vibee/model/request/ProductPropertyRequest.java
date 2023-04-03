package fpoly.edu.datn.vibee.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPropertyRequest {
    private double screenSize;
    private String screenResolution;
    private String screenTechnology;
    private String sweepFrequency;
    private String cameraFront;
    private String cameraRear;
    private String pinCapacity;
    private String chargingSpeed;
    private String chargingType;
    private String cpu;
    private String sim;
    private boolean isJack;
    private String os;
    private String weight;
    private String frameMaterial;
    private String backMaterial;
}

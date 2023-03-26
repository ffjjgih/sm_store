package fpoly.edu.datn.vibee.model.result;

import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandResult {
    private int id;
    private String name;
    private String status;
    private String images;
    private String description;
    private UploadFileResponse attachment;
    private String createdDate;
    private String createdBy;
    private String modifiedDate;
    private String modifiedBy;
    private int sumProduct;
    private int sumVersion;
    private int sumSeries;
    private int sumImport;
    private int sumExport;
    private String birthOfDate;
    private String email;
    private String website;
    private String address;
    private int rate;

}

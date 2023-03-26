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
public class CategoryResult {
    private int id;
    private String name;
    private String description;
    private int fileId;
    private String status;
    private String image;
    private UploadFileResponse attachment;
    private String createdDate;
    private String modifiedDate;
    private int sumProduct;
    private String createdBy;
    private String modifiedBy;
}

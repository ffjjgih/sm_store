package fpoly.edu.datn.vibee.model.result;

import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SupplierResult {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String status;
    private String description;
    private String createdDate;
    private String modifiedDate;
    private int sumImport;
    private int sumExport;
    private int sumImportReturn;
    private int sumExportReturn;
    private int countImport;
    private String image;
    private String importDate;
    private String createdBy;
    private String modifiedBy;
    private UploadFileResponse attachment;

}

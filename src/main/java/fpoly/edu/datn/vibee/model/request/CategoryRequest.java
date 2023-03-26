package fpoly.edu.datn.vibee.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private int id;
    private String name;
    private String description;
    private int attachmentId;
    private String status;
}

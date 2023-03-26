package fpoly.edu.datn.vibee.model.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Filter {
    private int page;
    private int size;
    private String sort;
    private String order;
    private String search;
    private String status;
    private String fromDate;
    private String toDate;
    private int sumPage;
}

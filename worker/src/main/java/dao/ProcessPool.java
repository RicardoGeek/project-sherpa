package dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ProcessPool {
    private int id;
    private String searchTerm;
    private String domain;
    private Date sinkDate;
}

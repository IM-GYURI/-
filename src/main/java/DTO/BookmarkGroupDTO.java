package DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookmarkGroupDTO {
    private int id;
    private String name;
    private int order_num;
    private String register_dttm;
    private String update_dttm;
}
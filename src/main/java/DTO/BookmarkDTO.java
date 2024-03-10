package DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookmarkDTO {
    private int id;
    private int group_no;
    private String mgr_no;
    private String register_dttm;
}
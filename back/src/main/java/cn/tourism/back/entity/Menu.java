package cn.tourism.back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: xinYue
 * @time: 2019/11/23 16:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    private String icon;

    private String title;

    private Integer seq;

}

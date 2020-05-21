package entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @program: tensquare-project
 * @description:
 **/
@Data
@ToString
@AllArgsConstructor
public class PageResult<T> implements Serializable{

    private long total;
    private List<T> rows;


}

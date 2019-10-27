package com.xxl.job.admin.beetl;

import org.beetl.sql.core.JPAEntityHelper;
import org.beetl.sql.core.NameConversion;
import org.beetl.sql.core.UnderlinedNameConversion;

import java.util.Map;

/**
 * Underlined and JPA Name Conversion
 *
 * @author Mars
 * @date 2019/10/26
 */
public class UnderlinedJpaNameConversion extends NameConversion {
    NameConversion nc = null;
    public UnderlinedJpaNameConversion(){
        nc=new UnderlinedNameConversion();
    }
    /**
     * 对于没有jpa注解的，采用的命名策略，包括tail的命名策略，如果nc为null，则直接返回列名
     * @param nc
     */
    public UnderlinedJpaNameConversion(NameConversion nc){
        this.nc = nc !=null ? nc:new UnderlinedNameConversion();
    }

    @Override
    public String getColName(Class<?> c, String attrName) {
        if(c==null|| Map.class.isAssignableFrom(c)){
            return nc!=null?nc.getColName(attrName):attrName;
        }
        return JPAEntityHelper.getEntityTable(c,nc).getCol(attrName);
    }

    @Override
    public String getPropertyName(Class<?> c, String colName) {
        if(c==null||Map.class.isAssignableFrom(c)){
            return nc!=null?nc.getPropertyName(c, colName):colName;
        }
        //col到property是可能有对应关系的，即使property被标注了Transient
        String prop = JPAEntityHelper.getEntityTable(c,nc).getProp(colName);
        if(prop!=null) {
            return prop;
        }
        return nc!=null?nc.getPropertyName(c, colName):colName;
    }

    @Override
    public String getTableName(Class<?> c) {
        return JPAEntityHelper.getEntityTable(c,nc).getName();
    }

}

package org.jaws.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
@AttributeOverride(name = "id", column = @Column(name = "CATEGORY_OID"))
public class CategoryDO extends AbstractEntity{

    @Column(name = "CATEGORYDEF", nullable = false)
    private String propertyDef;

    @Column(name = "PRODUCT_OID", nullable = false)
    private String product;

    @Column(name = "MANDATORY", nullable = false)
    private Boolean mandatory;

    @Column(name = "CATEGORY_TYPE", nullable = false)
    private String propertyType;

    @Column(name = "PROPERTY_VALUE", nullable = false)
    private String value;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}

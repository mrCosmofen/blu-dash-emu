package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Status;

/**
 * A DataModel.
 */
@Entity
@Table(name = "data_model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "label")
    private String label;

    @Column(name = "data_format")
    private String dataFormat;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "precision")
    private Integer precision;

    @Enumerated(EnumType.STRING)
    @Column(name = "values")
    private Status values;

    @OneToOne(mappedBy = "dataModel")
    @JsonIgnore
    private QueryData queryData;

    @ManyToOne
    @JsonIgnoreProperties("dataModels")
    private DataSet dataSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public DataModel key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public DataModel label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public DataModel dataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
        return this;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public DataModel maxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getPrecision() {
        return precision;
    }

    public DataModel precision(Integer precision) {
        this.precision = precision;
        return this;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Status getValues() {
        return values;
    }

    public DataModel values(Status values) {
        this.values = values;
        return this;
    }

    public void setValues(Status values) {
        this.values = values;
    }

    public QueryData getQueryData() {
        return queryData;
    }

    public DataModel queryData(QueryData queryData) {
        this.queryData = queryData;
        return this;
    }

    public void setQueryData(QueryData queryData) {
        this.queryData = queryData;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public DataModel dataSet(DataSet dataSet) {
        this.dataSet = dataSet;
        return this;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataModel)) {
            return false;
        }
        return id != null && id.equals(((DataModel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataModel{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", label='" + getLabel() + "'" +
            ", dataFormat='" + getDataFormat() + "'" +
            ", maxLength=" + getMaxLength() +
            ", precision=" + getPrecision() +
            ", values='" + getValues() + "'" +
            "}";
    }
}

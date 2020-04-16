package com.bludash.emulator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A QueryData.
 */
@Entity
@Table(name = "query_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QueryData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_value")
    private String dataValue;

    @OneToOne
    @JoinColumn(unique = true)
    private DataModel dataModel;

    @ManyToOne
    @JsonIgnoreProperties("queryData")
    private Record record;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataValue() {
        return dataValue;
    }

    public QueryData dataValue(String dataValue) {
        this.dataValue = dataValue;
        return this;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public QueryData dataModel(DataModel dataModel) {
        this.dataModel = dataModel;
        return this;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public Record getRecord() {
        return record;
    }

    public QueryData record(Record record) {
        this.record = record;
        return this;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QueryData)) {
            return false;
        }
        return id != null && id.equals(((QueryData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "QueryData{" +
            "id=" + getId() +
            ", dataValue='" + getDataValue() + "'" +
            "}";
    }
}

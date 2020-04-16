package com.bludash.emulator.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The Dataset entity.\n@author A true hipster
 */
@ApiModel(description = "The Dataset entity.\n@author A true hipster")
@Entity
@Table(name = "data_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "key")
    private UUID key;

    @Column(name = "label")
    private String label;

    @Column(name = "landing_page")
    private String landingPage;

    @OneToMany(mappedBy = "dataSet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DataModel> dataModels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getKey() {
        return key;
    }

    public DataSet key(UUID key) {
        this.key = key;
        return this;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public DataSet label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public DataSet landingPage(String landingPage) {
        this.landingPage = landingPage;
        return this;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    public Set<DataModel> getDataModels() {
        return dataModels;
    }

    public DataSet dataModels(Set<DataModel> dataModels) {
        this.dataModels = dataModels;
        return this;
    }

    public DataSet addDataModel(DataModel dataModel) {
        this.dataModels.add(dataModel);
        dataModel.setDataSet(this);
        return this;
    }

    public DataSet removeDataModel(DataModel dataModel) {
        this.dataModels.remove(dataModel);
        dataModel.setDataSet(null);
        return this;
    }

    public void setDataModels(Set<DataModel> dataModels) {
        this.dataModels = dataModels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataSet)) {
            return false;
        }
        return id != null && id.equals(((DataSet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataSet{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", label='" + getLabel() + "'" +
            ", landingPage='" + getLandingPage() + "'" +
            "}";
    }
}

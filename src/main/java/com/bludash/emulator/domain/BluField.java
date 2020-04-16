package com.bludash.emulator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.bludash.emulator.domain.enumeration.BluFieldType;

/**
 * A BluField.
 */
@Entity
@Table(name = "blu_field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BluField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "label")
    private String label;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type")
    private BluFieldType dataType;

    @Column(name = "data_format")
    private String dataFormat;

    @Column(name = "values")
    private String values;

    @OneToOne(mappedBy = "bluField")
    @JsonIgnore
    private BluFieldStringValue bluFieldStringValue;

    @OneToOne(mappedBy = "bluField")
    @JsonIgnore
    private BluFieldCurrencyValue bluFieldCurrencyValue;

    @OneToOne(mappedBy = "bluField")
    @JsonIgnore
    private BluFieldNumberValue bluFieldNumberValue;

    @ManyToOne
    @JsonIgnoreProperties("bluFields")
    private BluForm bluForm;

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

    public BluField key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public BluField label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BluFieldType getDataType() {
        return dataType;
    }

    public BluField dataType(BluFieldType dataType) {
        this.dataType = dataType;
        return this;
    }

    public void setDataType(BluFieldType dataType) {
        this.dataType = dataType;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public BluField dataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
        return this;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getValues() {
        return values;
    }

    public BluField values(String values) {
        this.values = values;
        return this;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public BluFieldStringValue getBluFieldStringValue() {
        return bluFieldStringValue;
    }

    public BluField bluFieldStringValue(BluFieldStringValue bluFieldStringValue) {
        this.bluFieldStringValue = bluFieldStringValue;
        return this;
    }

    public void setBluFieldStringValue(BluFieldStringValue bluFieldStringValue) {
        this.bluFieldStringValue = bluFieldStringValue;
    }

    public BluFieldCurrencyValue getBluFieldCurrencyValue() {
        return bluFieldCurrencyValue;
    }

    public BluField bluFieldCurrencyValue(BluFieldCurrencyValue bluFieldCurrencyValue) {
        this.bluFieldCurrencyValue = bluFieldCurrencyValue;
        return this;
    }

    public void setBluFieldCurrencyValue(BluFieldCurrencyValue bluFieldCurrencyValue) {
        this.bluFieldCurrencyValue = bluFieldCurrencyValue;
    }

    public BluFieldNumberValue getBluFieldNumberValue() {
        return bluFieldNumberValue;
    }

    public BluField bluFieldNumberValue(BluFieldNumberValue bluFieldNumberValue) {
        this.bluFieldNumberValue = bluFieldNumberValue;
        return this;
    }

    public void setBluFieldNumberValue(BluFieldNumberValue bluFieldNumberValue) {
        this.bluFieldNumberValue = bluFieldNumberValue;
    }

    public BluForm getBluForm() {
        return bluForm;
    }

    public BluField bluForm(BluForm bluForm) {
        this.bluForm = bluForm;
        return this;
    }

    public void setBluForm(BluForm bluForm) {
        this.bluForm = bluForm;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BluField)) {
            return false;
        }
        return id != null && id.equals(((BluField) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BluField{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", label='" + getLabel() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", dataFormat='" + getDataFormat() + "'" +
            ", values='" + getValues() + "'" +
            "}";
    }
}

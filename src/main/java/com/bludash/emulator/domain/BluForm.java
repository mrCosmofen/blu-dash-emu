package com.bludash.emulator.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A BluForm.
 */
@Entity
@Table(name = "blu_form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BluForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "key")
    private UUID key;

    @Column(name = "label")
    private String label;

    @Column(name = "category")
    private String category;

    @Column(name = "product_key")
    private String productKey;

    @Column(name = "polling_interval")
    private Long pollingInterval;

    @Column(name = "modified")
    private Long modified;

    @OneToMany(mappedBy = "bluForm")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BluField> bluFields = new HashSet<>();

    @OneToMany(mappedBy = "bluForm")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BluFormData> bluFormData = new HashSet<>();

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

    public BluForm key(UUID key) {
        this.key = key;
        return this;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public BluForm label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public BluForm category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductKey() {
        return productKey;
    }

    public BluForm productKey(String productKey) {
        this.productKey = productKey;
        return this;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public Long getPollingInterval() {
        return pollingInterval;
    }

    public BluForm pollingInterval(Long pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }

    public void setPollingInterval(Long pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public Long getModified() {
        return modified;
    }

    public BluForm modified(Long modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public Set<BluField> getBluFields() {
        return bluFields;
    }

    public BluForm bluFields(Set<BluField> bluFields) {
        this.bluFields = bluFields;
        return this;
    }

    public BluForm addBluField(BluField bluField) {
        this.bluFields.add(bluField);
        bluField.setBluForm(this);
        return this;
    }

    public BluForm removeBluField(BluField bluField) {
        this.bluFields.remove(bluField);
        bluField.setBluForm(null);
        return this;
    }

    public void setBluFields(Set<BluField> bluFields) {
        this.bluFields = bluFields;
    }

    public Set<BluFormData> getBluFormData() {
        return bluFormData;
    }

    public BluForm bluFormData(Set<BluFormData> bluFormData) {
        this.bluFormData = bluFormData;
        return this;
    }

    public BluForm addBluFormData(BluFormData bluFormData) {
        this.bluFormData.add(bluFormData);
        bluFormData.setBluForm(this);
        return this;
    }

    public BluForm removeBluFormData(BluFormData bluFormData) {
        this.bluFormData.remove(bluFormData);
        bluFormData.setBluForm(null);
        return this;
    }

    public void setBluFormData(Set<BluFormData> bluFormData) {
        this.bluFormData = bluFormData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BluForm)) {
            return false;
        }
        return id != null && id.equals(((BluForm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BluForm{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", label='" + getLabel() + "'" +
            ", category='" + getCategory() + "'" +
            ", productKey='" + getProductKey() + "'" +
            ", pollingInterval=" + getPollingInterval() +
            ", modified=" + getModified() +
            "}";
    }
}

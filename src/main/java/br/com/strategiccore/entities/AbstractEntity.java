package br.com.strategiccore.entities;

import br.com.strategiccore.utils.Config;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Eduardo Folly
 */
@SuppressWarnings("unused")
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonbTransient
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Schema(hidden = true)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Schema(hidden = true)
    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt = Config.NOT_DELETED;

    @JsonbTransient
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @JsonbTransient
    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonbTransient
    @Column(name = "deleted_by")
    private Long deletedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

//    public void merge(AbstractEntity other) {
//        this.createdAt = other.createdAt;
//        this.updatedAt = other.updatedAt;
//        this.deletedAt = other.deletedAt;
//        this.createdBy = other.createdBy;
//        this.updatedBy = other.updatedBy;
//        this.deletedBy = other.deletedBy;
//    }
}

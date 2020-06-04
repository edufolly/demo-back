package br.com.strategiccore.entities;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author Eduardo Folly
 */
@SuppressWarnings("unused")
@Entity
@Audited
@Table(name = "sys_endpoint",
        indexes = {@Index(columnList = "url, deleted_at", unique = true)})
public class Endpoint extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "icon_name")
    private String iconName;

    @Column(name = "listed", nullable = false,
            columnDefinition = "boolean default true")
    private boolean listed = true;

    @Column(name = "is_web", nullable = false,
            columnDefinition = "boolean default true")
    private boolean isWeb = true;

    @Column(name = "is_android", nullable = false,
            columnDefinition = "boolean default true")
    private boolean isAndroid = false;

    @Column(name = "is_offline", nullable = false,
            columnDefinition = "boolean default false")
    private boolean isOffline = false;

    public Endpoint() {
    }

    public Endpoint(String name, String url, String iconName, boolean listed) {
        this.name = name;
        this.url = url;
        this.iconName = iconName;
        this.listed = listed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public boolean isListed() {
        return listed;
    }

    public void setListed(boolean listed) {
        this.listed = listed;
    }

    public boolean isWeb() {
        return isWeb;
    }

    public void setWeb(boolean web) {
        isWeb = web;
    }

    public boolean isAndroid() {
        return isAndroid;
    }

    public void setAndroid(boolean android) {
        isAndroid = android;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

//    public void merge(Endpoint other) {
//        super.merge(other);
//        this.name = other.name;
//        this.url = other.url;
//        this.iconName = other.iconName;
//        this.listed = other.listed;
//        this.isWeb = other.isWeb;
//        this.isAndroid = other.isAndroid;
//        this.isOffline = other.isOffline;
//    }
}

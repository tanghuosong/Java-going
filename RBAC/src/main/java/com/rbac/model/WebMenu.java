package com.rbac.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: ths
 * @date: 2017/10/23
 * @time: 11:43
 * @description:
 */
@Entity
@Table(name = "webmenu")
public class WebMenu implements Serializable{

    private Integer id;
    private String name;
    private String icon;

    private Integer mpid;
    private Integer bpid;
    private String route;

    public WebMenu() {
    }

    public WebMenu(Integer id, String name, String icon, Integer mpid, Integer bpid, String route) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.mpid = mpid;
        this.bpid = bpid;
        this.route = route;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getMpid() {
        return mpid;
    }

    public void setMpid(Integer mpid) {
        this.mpid = mpid;
    }

    public Integer getBpid() {
        return bpid;
    }

    public void setBpid(Integer bpid) {
        this.bpid = bpid;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "WebMenu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", mpid=" + mpid +
                ", bpid=" + bpid +
                ", route='" + route + '\'' +
                '}';
    }
}

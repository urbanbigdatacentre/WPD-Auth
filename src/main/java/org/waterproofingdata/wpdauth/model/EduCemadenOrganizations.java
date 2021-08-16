package org.waterproofingdata.wpdauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "educemaden_organizations")
@Getter
@Setter
@NoArgsConstructor
public class EduCemadenOrganizations {
    @Id
    private Integer id;

    @Column(nullable = true)
    private String active;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String creation_date;

    @Column(nullable = true)
    private String inep_code;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = true)
    private String type;

    @Column(nullable = true)
    private String website;

    @Column(nullable = true)
    private String login;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String responsible;
}

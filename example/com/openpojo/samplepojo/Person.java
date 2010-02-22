package com.openpojo.samplepojo;

import java.io.Serializable;
import java.sql.Timestamp;

public final class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for persistence service loading.
     */
    public Person() {
    }

    /**
     * Minimal business constructor.
     * 
     * @param firstname
     * @param middlename
     * @param lastname
     */
    public Person(final String firstname, final String middlename, final String lastname) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
    }

    /**
     * Full Constructor.
     * 
     * @param id
     * @param firstname
     * @param middlename
     * @param lastname
     * @param created
     * @param lastupdated
     */
    public Person(final String id, final String firstname, final String middlename, final String lastname,
            final Timestamp created, final Timestamp lastupdated) {
        this(firstname, lastname, middlename);
        this.id = id;
        this.created = created;
        this.lastupdated = lastupdated;

    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    private String firstname;
    private String middlename;
    private String lastname;

    private Timestamp created;
    private Timestamp lastupdated;

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname
     *            the firstname to set
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the middlename
     */
    public String getMiddlename() {
        return middlename;
    }

    /**
     * @param middlename
     *            the middlename to set
     */
    public void setMiddlename(final String middlename) {
        this.middlename = middlename;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname
     *            the lastname to set
     */
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the created timestamp
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * @param created
     *          The created timestamp
     */
    public void setCreated(final Timestamp created) {
        this.created = created;
    }

    /**
     * @return the lastupdated timestamp.
     */
    public Timestamp getLastupdated() {
        return lastupdated;
    }

    /**
     * Set the last updated time.
     * @param lastupdated
     *          The lastupdated timestamp
     */
    public void setLastupdated(final Timestamp lastupdated) {
        this.lastupdated = lastupdated;
    }

    @Override
    public String toString() {
        return String.format("Person [id=%s, firstname=%s, middlename=%s, lastname=%s, created=%s, lastupdated=%s]",
                this.getId(), firstname, middlename, lastname, created, lastupdated);
    }

}

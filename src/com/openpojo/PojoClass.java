package com.openpojo;

import java.util.List;

public interface PojoClass {

    /**
     * Get all PojoFields defined in the class.
     * 
     * @return the pojoFields
     */
    public List<PojoField> getPojoFields();

    /**
     * Returns the fully qualified class name.
     * 
     * @return
     *         The String fully qualified class name.
     */
    public String getName();

    /**
     * Checks to see if class extends a certain type.
     * 
     * @param type
     *            The type in question.
     * @return
     *         True if class is subclass or implements an interface, otherwise false.
     */
    public boolean extendz(final Class<?> type);
    
    /**
     * This method returns the underlying class represented by this instance.
     * @return
     *      The class type wrapped by this PojoClass.
     */
    public Class<?> getClazz();

    /**
     * This method creates a new instance.
     * 
     * @return
     *         new instance of clazz.
     */
    public Object newInstance();

    /**
     * Checks to see if this class is a nested subclass.
     * 
     * @return
     *         True if it is a subclass, false otherwise.
     */
    public boolean isNestedClass();

    /**
     * Copy all contents from one Instance represented by this PojoClass to another.
     * @param from
     *          The Instance to copy from.
     * @param to
     *          The Instance to copy to.
     */
    public void copy(final Object from, final Object to);

}
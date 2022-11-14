/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.struts.webapp.el.exercise;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;


/**
 * General purpose test bean for Struts custom tag tests.
 *
 * @author Craig R. McClanahan
 * @author Martin F N Cooper
 * @version $Rev$ $Date: 2004-10-16 13:04:52 -0400 (Sat, 16 Oct 2004)
 *          $
 */

public class TestBean extends ActionForm {
    private static final long serialVersionUID = 8323139488730091727L;

    // ------------------------------------------------------------- Properties


    /**
     * A collection property where the elements of the collection are of type
     * <code>LabelValueBean</code>.
     */
    private Collection<LabelValueBean> beanCollection = null;

    public Collection<LabelValueBean> getBeanCollection() {
        if (beanCollection == null) {
            ArrayList<LabelValueBean> entries = new ArrayList<>(10);

            entries.add(new LabelValueBean("Label 0", "Value 0"));
            entries.add(new LabelValueBean("Label 1", "Value 1"));
            entries.add(new LabelValueBean("Label 2", "Value 2"));
            entries.add(new LabelValueBean("Label 3", "Value 3"));
            entries.add(new LabelValueBean("Label 4", "Value 4"));
            entries.add(new LabelValueBean("Label 5", "Value 5"));
            entries.add(new LabelValueBean("Label 6", "Value 6"));
            entries.add(new LabelValueBean("Label 7", "Value 7"));
            entries.add(new LabelValueBean("Label 8", "Value 8"));
            entries.add(new LabelValueBean("Label 9", "Value 9"));

            beanCollection = entries;
        }

        return (beanCollection);
    }

    public void setBeanCollection(Collection<LabelValueBean> beanCollection) {
        this.beanCollection = beanCollection;
    }


    /**
     * A multiple-String SELECT element using a bean collection.
     */
    private String[] beanCollectionSelect = { "Value 1", "Value 3",
            "Value 5" };

    public String[] getBeanCollectionSelect() {
        return (this.beanCollectionSelect);
    }

    public void setBeanCollectionSelect(String beanCollectionSelect[]) {
        this.beanCollectionSelect = beanCollectionSelect;
    }


    /**
     * A boolean property whose initial value is true.
     */
    private boolean booleanProperty = true;

    public boolean getBooleanProperty() {
        return (booleanProperty);
    }

    public void setBooleanProperty(boolean booleanProperty) {
        this.booleanProperty = booleanProperty;
    }


    /**
     * A multiple-String SELECT element using a collection.
     */
    private String[] collectionSelect = { "Value 2", "Value 4",
            "Value 6" };

    public String[] getCollectionSelect() {
        return (this.collectionSelect);
    }

    public void setCollectionSelect(String collectionSelect[]) {
        this.collectionSelect = collectionSelect;
    }


    /**
     * A double property.
     */
    private double doubleProperty = 321.0;

    public double getDoubleProperty() {
        return (this.doubleProperty);
    }

    public void setDoubleProperty(double doubleProperty) {
        this.doubleProperty = doubleProperty;
    }


    /**
     * A boolean property whose initial value is false
     */
    private boolean falseProperty = false;

    public boolean getFalseProperty() {
        return (falseProperty);
    }

    public void setFalseProperty(boolean falseProperty) {
        this.falseProperty = falseProperty;
    }


    /**
     * A float property.
     */
    private float floatProperty = (float) 123.0;

    public float getFloatProperty() {
        return (this.floatProperty);
    }

    public void setFloatProperty(float floatProperty) {
        this.floatProperty = floatProperty;
    }


    /**
     * Integer arrays that are accessed as an array as well as indexed.
     */
    private int intArray[] = { 0, 10, 20, 30, 40 };

    public int[] getIntArray() {
        return (this.intArray);
    }

    public void setIntArray(int intArray[]) {
        this.intArray = intArray;
    }

    private int intIndexed[] = { 0, 10, 20, 30, 40 };

    public int getIntIndexed(int index) {
        return (intIndexed[index]);
    }

    public void setIntIndexed(int index, int value) {
        intIndexed[index] = value;
    }


    private int intMultibox[] = new int[0];

    public int[] getIntMultibox() {
        return (this.intMultibox);
    }

    public void setIntMultibox(int intMultibox[]) {
        this.intMultibox = intMultibox;
    }

    /**
     * An integer property.
     */
    private int intProperty = 123;

    public int getIntProperty() {
        return (this.intProperty);
    }

    public void setIntProperty(int intProperty) {
        this.intProperty = intProperty;
    }


    /**
     * A long property.
     */
    private long longProperty = 321;

    public long getLongProperty() {
        return (this.longProperty);
    }

    public void setLongProperty(long longProperty) {
        this.longProperty = longProperty;
    }


    /**
     * A multiple-String SELECT element.
     */
    private String[] multipleSelect = { "Multiple 3", "Multiple 5",
            "Multiple 7" };

    public String[] getMultipleSelect() {
        return (this.multipleSelect);
    }

    public void setMultipleSelect(String multipleSelect[]) {
        this.multipleSelect = multipleSelect;
    }


    /**
     * A nested reference to another test bean (populated as needed).
     */
    private TestBean nested = null;

    public TestBean getNested() {
        if (nested == null) {
            nested = new TestBean();
        }
        return (nested);
    }


    /**
     * A String property with an initial value of null.
     */
    private String nullProperty = null;

    public String getNullProperty() {
        return (this.nullProperty);
    }

    public void setNullProperty(String nullProperty) {
        this.nullProperty = nullProperty;
    }


    /**
     * A short property.
     */
    private short shortProperty = (short) 987;

    public short getShortProperty() {
        return (this.shortProperty);
    }

    public void setShortProperty(short shortProperty) {
        this.shortProperty = shortProperty;
    }


    /**
     * A single-String value for a SELECT element.
     */
    private String singleSelect = "Single 5";

    public String getSingleSelect() {
        return (this.singleSelect);
    }

    public void setSingleSelect(String singleSelect) {
        this.singleSelect = singleSelect;
    }


    /**
     * String arrays that are accessed as an array as well as indexed.
     */
    private String stringArray[] =
            { "String 0", "String 1", "String 2", "String 3", "String 4" };

    public String[] getStringArray() {
        return (this.stringArray);
    }

    public void setStringArray(String stringArray[]) {
        this.stringArray = stringArray;
    }

    private String stringIndexed[] =
            { "String 0", "String 1", "String 2", "String 3", "String 4" };

    public String getStringIndexed(int index) {
        return (stringIndexed[index]);
    }

    public void setStringIndexed(int index, String value) {
        stringIndexed[index] = value;
    }

    private String indexedStrings[] =
            { "alpha", "beta", "gamma", "delta", "epsilon" };

    public String[] getIndexedStrings() {
        return (indexedStrings);
    }

    private String stringMultibox[] = new String[0];

    public String[] getStringMultibox() {
        return (this.stringMultibox);
    }

    public void setStringMultibox(String stringMultibox[]) {
        this.stringMultibox = stringMultibox;
    }

    /**
     * A String property.
     */
    private String stringProperty = "This is a string";

    public String getStringProperty() {
        return (this.stringProperty);
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    /**
     * An empty String property.
     */
    private String emptyStringProperty = "";

    public String getEmptyStringProperty() {
        return (this.emptyStringProperty);
    }

    public void setEmptyStringProperty(String emptyStringProperty) {
        this.emptyStringProperty = emptyStringProperty;
    }

    /**
     * A list of coordinate objects.
     */
    private Coord[] coords =
            { new Coord(0, 0), new Coord(0, 1), new Coord(1, 1),
                    new Coord(2, 0),
                    new Coord(2, 1)
            };

    public Coord[]  getCoords() {
        return (coords);
    }

    public Coord getCoord(int index) {
        return (coords[index]);
    }

    public void setCoord(int index, Coord coord) {
        coords[index] = coord;
    }

    /**
     * A list of images.
     */
    public String images[] = { "T1.gif", "T2.gif" };

    public String[] getImages() {
        return (images);
    }

    private Coord[] imageCoords = { new Coord(0, 0), new Coord(0, 0) };

    public Coord[]  getImageCoords() {
        return (imageCoords);
    }

    public Coord getImageCoord(int index) {
        return (imageCoords[index]);
    }

    public void setImageCoord(int index, Coord coord) {
        imageCoords[index] = coord;
    }

    /**
     * A property that allows a null value but is still used in a SELECT.
     */
    private String withNulls = null;

    public String getWithNulls() {
        return (this.withNulls);
    }

    public void setWithNulls(String withNulls) {
        this.withNulls = withNulls;
    }

    // --------------------------------------------------------- Public Methods


    /**
     * Reset the properties that will be received as input.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        booleanProperty = false;
        collectionSelect = new String[0];
        intMultibox = new int[0];
        multipleSelect = new String[0];
        stringMultibox = new String[0];
        if (nested != null) {
            nested.reset(mapping, request);
        }

    }


}

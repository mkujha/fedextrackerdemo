//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.12 at 08:00:32 AM EST 
//


package com.demo.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrackingDateOrTimestamp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrackingDateOrTimestamp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://demo.com/ws/track/v12}TrackingDateOrTimestampType" minOccurs="0"/>
 *         &lt;element name="DateOrTimestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrackingDateOrTimestamp", propOrder = {
    "type",
    "dateOrTimestamp"
})
public class TrackingDateOrTimestamp {

    @XmlElement(name = "Type")
    protected TrackingDateOrTimestampType type;
    @XmlElement(name = "DateOrTimestamp")
    protected String dateOrTimestamp;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link TrackingDateOrTimestampType }
     *     
     */
    public TrackingDateOrTimestampType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrackingDateOrTimestampType }
     *     
     */
    public void setType(TrackingDateOrTimestampType value) {
        this.type = value;
    }

    /**
     * Gets the value of the dateOrTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOrTimestamp() {
        return dateOrTimestamp;
    }

    /**
     * Sets the value of the dateOrTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOrTimestamp(String value) {
        this.dateOrTimestamp = value;
    }

}

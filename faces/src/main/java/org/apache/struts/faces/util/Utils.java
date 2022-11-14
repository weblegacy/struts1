package org.apache.struts.faces.util;

import java.util.Map;

import jakarta.el.ELException;
import jakarta.el.MethodExpression;
import jakarta.el.ValueExpression;
import jakarta.faces.component.ActionSource2;
import jakarta.faces.component.UIComponent;
import jakarta.faces.event.MethodExpressionActionListener;

/**
 * This class has some static utils-methods.
 *
 * @author Stefan Graff
 *
 * @since Struts 1.4.1
 */
public class Utils {

    /**
     * Class is not instantiable.
     */
    private Utils() {
    }

    /**
     * If the specified attribute value is not {@code null} use it
     * to either store a value binding expression for the specified
     * attribute name, or store it as the literal value of the
     * attribute.
     *
     * @param component {@code UIComponent} whose attribute is to
     *                  be set
     * @param propName  Property name
     * @param value     Property value (or {@code null})
     *
     * @exception ELException if the expression has invalid syntax
     *
     * @since 1.4.1
     */
    public static void setBooleanProperty(UIComponent component,
            String propName, ValueExpression value) {

        setBooleanProperty(component, propName, value, null);
    }

    /**
     * If the specified attribute value is not {@code null} use it
     * to either store a value binding expression for the specified
     * attribute name, or store it as the literal value of the
     * attribute.
     *
     * @param component {@code UIComponent} whose attribute is to
     *                  be set
     * @param propName  Property name
     * @param value     Property value (or {@code null})
     * @param defaultValue When not {@code null} the default-value
     *
     * @exception ELException if the expression has invalid syntax
     *
     * @since 1.4.3
     */
    public static void setBooleanProperty(UIComponent component,
            String propName, ValueExpression value, Boolean defaultValue) {

        if (value == null) {
            if (defaultValue != null) {
                component.getAttributes().put(propName, defaultValue);
            }

            return;
        }

        if (value.isLiteralText()) {
            component.getAttributes().put(propName, Boolean.valueOf(value.getExpressionString()));
        } else {
            component.setValueExpression(propName, value);
        }
    }

    /**
     * If the specified attribute value is not {@code null} use it
     * to either store a value binding expression for the specified
     * attribute name, or store it as the literal value of the
     * attribute.
     *
     * @param component    {@code UIComponent} whose attribute is to
     *                     be set
     * @param propName     Property name
     * @param value        Property value (or {@code null})
     *
     * @exception ELException if the expression has invalid syntax
     *
     * @since 1.4.1
     */
    public static void setStringProperty(UIComponent component,
            String propName, ValueExpression value) {

        setStringProperty(component, propName, value, null);
    }

    /**
     * If the specified attribute value is not {@code null} use it
     * to either store a value binding expression for the specified
     * attribute name, or store it as the literal value of the
     * attribute.
     *
     * @param component    {@code UIComponent} whose attribute is to
     *                     be set
     * @param propName     Property name
     * @param value        Property value (or {@code null})
     * @param defaultValue When not {@code null} the default-value
     *
     * @exception ELException if the expression has invalid syntax
     *
     * @since 1.4.3
     */
    public static void setStringProperty(UIComponent component,
            String propName, ValueExpression value, String defaultValue) {

        if (value == null) {
            if (defaultValue != null) {
                component.getAttributes().put(propName, defaultValue);
            }

            return;
        }

        if (value.isLiteralText()) {
            component.getAttributes().put(propName, value.getExpressionString());
        } else {
            component.setValueExpression(propName, value);
        }
    }

    /**
     * If the specified attribute value is not {@code null} use it
     * to either store a value binding expression for the specified
     * attribute name, or store it as the literal value of the
     * attribute.
     *
     * @param component {@code UIComponent} whose attribute is to
     *                  be set
     * @param propName  Property name
     * @param value     Property value (or {@code null})
     *
     * @exception ELException if the expression has invalid syntax
     *
     * @since 1.4.1
     */
    public static void setIntegerProperty(UIComponent component,
            String propName, ValueExpression value) {

        setIntegerProperty(component, propName, value, null);
    }

    /**
     * If the specified attribute value is not {@code null} use it
     * to either store a value binding expression for the specified
     * attribute name, or store it as the literal value of the
     * attribute.
     *
     * @param component {@code UIComponent} whose attribute is to
     *                  be set
     * @param propName  Property name
     * @param value     Property value (or {@code null})
     * @param defaultValue When not {@code null} the default-value
     *
     * @exception ELException if the expression has invalid syntax
     *
     * @since 1.4.3
     */
    public static void setIntegerProperty(UIComponent component,
            String propName, ValueExpression value, Integer defaultValue) {

        if (value == null) {
            if (defaultValue != null) {
                component.getAttributes().put(propName, defaultValue);
            }

            return;
        }

        if (value.isLiteralText()) {
            component.getAttributes().put(propName, Integer.valueOf(value.getExpressionString()));
        } else {
            component.setValueExpression(propName, value);
        }
    }

    /**
     * If the specified action is not {@code null} use it to
     * set the action of the component.
     *
     * @param component {@code UIComponent} whose action is to
     *                  be set
     * @param action    the Action
     *
     * @throws IllegalArgumentException if the component is not an
     *         instance of {@code ActionSource2}
     *
     * @since 1.4.1
     */
    public static void setActionProperty(UIComponent component, MethodExpression action) {
        if (action != null) {
            castActionSource2(component).setActionExpression(action);
        }
    }

    /**
     * If the specified action-listener is not {@code null} use
     * it to add the action-listener to the component.
     *
     * @param component      {@code UIComponent} whose action-listener
     *                       is to be added
     * @param actionListener the Action-Listener
     *
     * @throws IllegalArgumentException if the component is not an
     *         instance of {@code ActionSource2}
     *
     * @since 1.4.1
     */
    public static void setActionListenerProperty(UIComponent component, MethodExpression actionListener) {
        if (actionListener != null) {
            castActionSource2(component).addActionListener(new MethodExpressionActionListener(actionListener));
        }
    }

    /**
     * Test the component if it's an instance of {@code ActionSource2}
     * and returns it.
     *
     * @param component {@code UIComponent} to test
     *
     * @return the component as {@code ActionSource2}
     *
     * @throws IllegalArgumentException if the component is not an
     *         instance of {@code ActionSource2}
     *
     * @since 1.4.1
    */
    private static ActionSource2 castActionSource2(UIComponent component) {
        if (component instanceof ActionSource2) {
            return (ActionSource2) component;
        }
        throw new IllegalArgumentException("Component "
                + component.getClientId()
                + " is no ActionSource2");
    }

    /**
     * Returns the value of a map as expected class.
     *
     * @param <T> the expected class
     * @param clazz the expected class
     * @param map the map with the values
     * @param key the key to access the map
     *
     * @return the value as expected class
     *
     * @throws ClassCastException if the key is of an
     *     inappropriate type for this map or if the
     *     value is not null and is not assignable to
     *     the type T.
     * @throws NullPointerException if the specified key
     *     is null and this map does not permit null keys.
     */
    public static <T> T getMapValue(Class<T> clazz, Map<String, Object> map, String key) {
        final Object ret = map.get(key);
        return clazz.cast(ret);
    }
}
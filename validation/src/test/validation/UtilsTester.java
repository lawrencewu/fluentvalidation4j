package test.validation;

import com.lawrence.oss.validation.exception.ArgumentNullException;
import com.lawrence.oss.validation.util.Utils;
import org.junit.Test;
import validation.model.Address;

import java.lang.reflect.Member;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/10.
 */
public class UtilsTester extends BaseValidatorTester{

    @Test
    public void throw_if_instance_is_null(){
        try {
            Utils.getMember(null, "surname");
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentNullException);
        }
    }

    @Test
    public void throw_if_property_is_null(){
        Member member = Utils.getMember(person, null);
        assertNull(member);
    }

    @Test
    public void if_property_is_not_exist_return_null(){
        Member member = Utils.getMember(person, "surname1");
        assertNull(member);
    }

    @Test
    public void throw_if_instance_is_null_return_null(){
        try {
            Utils.isPropertyExist(null, "surname");
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentNullException);
        }
    }

    @Test
    public void throw_if_property_is_null_return_false(){
        Boolean result = Utils.isPropertyExist(person, (String[]) null);
        assertFalse(result);
    }

    @Test
    public void if_property_is_not_exist_return_false(){
        boolean result = Utils.isPropertyExist(person, "surname1");
        assertFalse(result);
    }

    @Test
    public void if_propagted_property_is_exist_return_true(){
        boolean result = Utils.isPropertyExist(person, "address.line1");
        assertTrue(result);
    }

    @Test
    public void if_property_is_int_return_zero(){
        Object result = Utils.getDefaultValueForProperty(person.getClass(), "id");
        assertEquals(0, result);
    }

    @Test
    public void if_property_is_double_return_zero_ponit_zero(){
        Object result = Utils.getDefaultValueForProperty(person.getClass(), "age");
        assertEquals(0.0, result);
    }

    @Test
    public void if_property_is_String_return_null(){
        Object result = Utils.getDefaultValueForProperty(person.getClass(), "surname");
        assertNull(result);
    }

    @Test
    public void if_property_is_Object_return_object(){
        Object result = Utils.getDefaultValueForProperty(person.getClass(), "address");
        assertNull(result);
    }

    @Test
    public void if_property_is_Date_return_null(){
        Object result = Utils.getDefaultValueForProperty(person.getClass(), "dateOfBirth");
        assertNull(result);
    }

    @Test
    public void if_property_is_a_List_return_null(){
        Object result = Utils.getDefaultValueForProperty(person.getClass(), "orders");
        assertTrue(result instanceof ArrayList);
        assertEquals(0, ((ArrayList) result).size());
    }


}

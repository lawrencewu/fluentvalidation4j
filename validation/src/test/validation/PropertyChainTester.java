package test.validation;

import com.lawrence.oss.validation.internal.PropertyChain;
import com.lawrence.oss.validation.internal.PropertyRule;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by z_wu on 2015/1/5.
 */
public class PropertyChainTester {

    PropertyChain chain;
    @Before
    public void setUp(){
        chain = new PropertyChain();
    }

    @Test
    public void calling_ToString_should_construct_string_representation_of_chain() {
        chain.add("child");
        chain.add("grandChild");
        final String expected = "child.grandChild";
        assertEquals(expected, chain.toString());
    }

    @Test
    public void should_be_subchain() {
        chain.add("parent");
        chain.add("child");
        PropertyChain childChain = new PropertyChain(chain);
        childChain.add("grandchild");
        assertTrue(childChain.isChildChainOf(chain));
    }

    @Test
    public void should_not_be_subchain() {
        chain.add("foo");
        PropertyChain otherChain = new PropertyChain();
        otherChain.add("bar");
        assertFalse(otherChain.isChildChainOf(chain));
    }

    public class Parent {
        public Child getChild() {
            return child;
        }

        public void setChild(Child child) {
            this.child = child;
        }

        public Child child;
    }

    public class Child {
        public Grandchild getGrandChild() {
            return grandChild;
        }

        public void setGrandChild(Grandchild grandChild) {
            this.grandChild = grandChild;
        }

        public Grandchild grandChild;
    }

    public class Grandchild {}
}

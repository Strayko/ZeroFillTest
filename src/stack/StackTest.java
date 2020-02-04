package stack;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

public class StackTest {
    private Stack stack;
    private int oneIndex;

    @Before
    public void setUp() throws Exception {
        stack = BoundedStack.Make(2);
    }

    @Test
    public void newlyCreateStack_ShouldBeEmpty() throws Exception {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.getSize());
    }

    @Test
    public void AfterOnePush_StackSizeShouldBeOne() throws Exception {
        stack.push(1);
        assertEquals(1, stack.getSize());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void afterOnePushAndOnePop_ShouldBeEmpty() throws Exception {
        stack.push(1);
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test(expected = BoundedStack.Overflow.class)
    public void WhenPushedPastLimit_StackOverflows() throws Exception {
        stack.push(1);
        stack.push(1);
        stack.push(1);
    }

    @Test(expected = BoundedStack.Underflow.class)
    public void WhenEmptyStackIsPopped_ShouldThrownUnderflow() throws Exception {
        stack.pop();
    }

    @Test
    public void WhenOneIsPushed_OneIsPopped() throws Exception {
        stack.push(1);
        assertEquals(1, stack.pop());
    }

    @Test
    public void whenOneAndTwoArePushed_TwoAndOneArePopped() throws Exception {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test(expected = BoundedStack.IllegalCapacity.class)
    public void whenCreatingStackWithNegativeSize_ShouldThrowIllegalCapacity() throws Exception {
        BoundedStack.Make(-1);
    }

    @Test(expected = BoundedStack.Overflow.class)
    public void whenCreatingStackWithZeroCapacity_AnyPushShouldOverflow() throws Exception {
        stack = BoundedStack.Make(0);
        stack.push(1);
    }

    @Test
    public void whenOneIsPushed_OneIsOnTop() throws Exception {
        stack.push(1);
        assertEquals(1, stack.top());
    }

    @Test(expected = Stack.Empty.class)
    public void WhenStackIsEmpty_TopThrowsEmpty() throws Exception {
        stack.top();
    }

    @Test(expected = Stack.Empty.class)
    public void WithZeroCapacityStack_topThrowsEmpty() throws Exception {
        stack = BoundedStack.Make(0);
        stack.top();
    }

    @Test
    public void GivenStackWithOneTwoPushed_FindOneAndTwo() throws Exception {
        stack.push(1);
        stack.push(2);
        int oneIndex = stack.find(1);
        int twoIndex = stack.find(2);
        assertEquals(1, stack.find(1).intValue());
        assertEquals(0, stack.find(2).intValue());
    }

    @Test
    public void GivenStackWithNo2_Find2ShouldReturnNull() throws Exception {
        assertNull(stack.find(2));
    }
}

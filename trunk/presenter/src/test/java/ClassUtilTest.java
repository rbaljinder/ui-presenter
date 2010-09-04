import junit.framework.TestCase;
import model.ToAnnotate;

import org.baljinder.presenter.util.ReflectionUtils;

/**
 * 
 */

/**
 * @author Baljinder Randhawa
 *
 */
public class ClassUtilTest extends TestCase {

	public void test(){
		ToAnnotate system = new ToAnnotate();
		system.setTest("ME");
		assertEquals(ReflectionUtils.getFieldValue(system,"test"),"ME");
		ReflectionUtils.setFieldValue(system,"test", "YOU");
		assertEquals(system.getTest(),"YOU");
		System.out.println(system.getTest());
		//System.out.println(ClassUtils.getClassesForPackage("net.sf.hibernate"));
	}
}

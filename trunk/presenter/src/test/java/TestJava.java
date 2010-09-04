import junit.framework.TestCase;

/**
 * 
 */

/**
 * @author Baljinder Randhawa
 *
 */
public class TestJava extends TestCase {

	class A{
		C c ;
		B b= new B(c);
	}
	class B{
		C c ;
		public B(C c){
			this.c = c ;
		}
	}
	class C{
		public void print(){
			System.out.println("It works");
		}
	}
	
	public void testMe(){
		A a = new A();
		a.c = new C();
		a.c.print();
		a.b.c.print();
	}
}

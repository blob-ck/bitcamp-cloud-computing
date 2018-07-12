import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import bitcamp.pms.annoation.Component;
import bitcamp.pms.annoation.Controller;
import bitcamp.pms.annoation.Repository;

public class Test {

	static HashMap<String, Object> objPool = new HashMap<>();
	
	public static void main(String[] args) throws URISyntaxException {

		ClassLoader defaultClassLoader = ClassLoader.getSystemClassLoader();
		
		URL url = defaultClassLoader.getResource("bitcamp/pms");
		//URL url = ClassLoader.getSystemResource("bitcamp/pms");
		
		//상대경로를 통해서 파일을 가져오고, 아래에서 파일을 사용해 절대경로를 가져올 수 있다.
		File file = new File(url.toURI());
				
		System.out.println("url = "+url.toString());
		//파일을 가져오면 절대경로를 알 수 있다.
		System.out.println("AbsolutePath = "+file.getAbsolutePath());
		System.out.println("isDirectory = "+file.isDirectory());
		/*for(String filename : files) {
			System.out.println(filename);
		}*/
		
		//findClass(file, "bitcamp.pms");
		
	}



	static void findClass(File path, String  packageName) {
		
		//필터를 쓰게 되면 디렉토리명과 .class로 끝나는 것만 파일리스트에 추가
/*		class MyFilter implements FileFilter {
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return true;
				}
				
				if (pathname.isFile() && pathname.getName().endsWith(".class")) {
					return true;
				}
				
				return false;
			}
		}*/
		
		//이렇게 작성하면 위아래로 필터가 뭐하는 넘인지 확인해야하므로, 파라미터로 익명클래스를 작성해서 넣어버린다.
		//File[] subFiles = path.listFiles(new MyFilter());
		
		//자바는 메소드를 하나 만들더라도 반드시 클래스로 감싸야 했다.
		/*File[] subFiles = path.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {return true;}
				if (pathname.isFile() && pathname.getName().endsWith(".class")) {return true;}
				return false;
			}
		});*/
		
		//이런 클래스 작성 후에 메소드를 사용하는 번거로운 방식을 해결하기 위해 도입한게 람다.
		//jsp처럼, out.print를 자동으로 붙여주는 작업을 하는거라 이해하면 된다.
		//필요한 메소드만 구현하면 알아서 익명 클래스로 감싸주는 게 람다
		File[] subFiles = path.listFiles((File pathname) -> {
			if (pathname.isDirectory()) {return true;}
			if (pathname.isFile() && pathname.getName().endsWith(".class")) {return true;}
			return false;
		});
		
		for (File subFile : subFiles) {
			if(subFile.isFile()) {
				//System.out.println(packageName + "." + subFile.getName());
				
				//.class확장자 안보이게
				String className = packageName + "." + subFile.getName().replace(".class", "");
				//System.out.println(className);
				
				//이제 가져온 이름을 가지고 객체를 만드는 작업
				createObject(className);
				
			} else {
				//재귀
				findClass(subFile, packageName + "." + subFile.getName());
			}
		}
	}
	
	
	
	private static void createObject(String className) {
		try {
			
			//클래스 이름(패키지명+클래스명)으로  .class 파일을 찾아 로딩한다
			Class<?> clazz = Class.forName(className);
			
			//@Component, @Controller, @Repository 가 붙지 않은 클래스라면 객체를 생성하지 않는다.
			if (clazz.getAnnotation(Component.class) == null &&
					clazz.getAnnotation(Controller.class) ==null &&
					clazz.getAnnotation(Repository.class) ==null
					) {
				return;
			}
			
			
			//객체를 저장할 때 사용할 이름을 알아낸다.
			String objName = getObjectName(clazz);
			System.out.println(objName);
			
			// 클래스 정보를 보고 기본 생성자를 알아낸다.
			Constructor<?> constructor = clazz.getConstructor();
			
			//기본생성자를 호출하여 객체를 생성한 후 객체 보관소에 저장한다.
			objPool.put(objName, constructor.newInstance());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	private static String getObjectName(Class<?> clazz) throws Exception {
		
		String objName = null;
		
		Component compAnno = clazz.getAnnotation(Component.class);
		if(compAnno != null) {
			objName = compAnno.value();
		}
		
		Controller ctrlAnno = clazz.getAnnotation(Controller.class);
		if(ctrlAnno != null) {
			objName = ctrlAnno.value();
		}
		
		Repository repoAnno = clazz.getAnnotation(Repository.class);
		if(repoAnno != null) {
			objName = repoAnno.value();
		}
		
		//System.out.println(objName);

		//스프링은 어노테이션 이름을 명시하지 않으면 패키지.클래스명 을 어노테이션명으로 지정한다.
		if (objName.length()==0) {
			return clazz.getCanonicalName();
		} else {
			return objName;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

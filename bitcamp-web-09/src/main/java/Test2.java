import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import bitcamp.pms.annoation.Component;
import bitcamp.pms.annoation.Controller;
import bitcamp.pms.annoation.Repository;

public class Test2 {

	static HashMap<String, Object> objPool = new HashMap<>();
	
	public static void main(String[] args) throws URISyntaxException {

		ClassLoader defaultClassLoader = ClassLoader.getSystemClassLoader();
		
		URL url = defaultClassLoader.getResource("bitcamp/pms");
		File file = new File(url.toURI());
				
		findClass(file, "bitcamp.pms");
		
		injectDependency();
	}

	
	private static void injectDependency() {

		
	}


	static void findClass(File path, String  packageName) {
		File[] subFiles = path.listFiles((File pathname) -> {
			if (pathname.isDirectory()) {return true;}
			if (pathname.isFile() && pathname.getName().endsWith(".class")) {return true;}
			return false;
		});
		
		for (File subFile : subFiles) {
			if(subFile.isFile()) {
				String className = packageName + "." + subFile.getName().replace(".class", "");
				createObject(className);
			} else {
				findClass(subFile, packageName + "." + subFile.getName());
			}
		}
	}
	
	
	
	private static void createObject(String className) {
		try {
			
			Class<?> clazz = Class.forName(className);
			
			if (clazz.getAnnotation(Component.class) == null &&
					clazz.getAnnotation(Controller.class) ==null &&
					clazz.getAnnotation(Repository.class) ==null
					) {
				return;
			}
			
			
			String objName = getObjectName(clazz);
			System.out.println(objName);
			
			Constructor<?> constructor = clazz.getConstructor();
			
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
		
		if (objName.length()==0) {
			return clazz.getCanonicalName();
		} else {
			return objName;
		}
	}
}

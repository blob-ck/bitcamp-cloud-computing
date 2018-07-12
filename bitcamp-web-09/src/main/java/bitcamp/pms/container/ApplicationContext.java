package bitcamp.pms.container;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

import org.apache.ibatis.io.Resources;

import bitcamp.pms.annoation.Autowired;
import bitcamp.pms.annoation.Component;
import bitcamp.pms.annoation.Controller;
import bitcamp.pms.annoation.Repository;

public class ApplicationContext {
	
	HashMap<String, Object> objPool = new HashMap<>();
	
	
	//1.생성자
	public ApplicationContext(String packageName) throws Exception {
		System.out.println("packageName : "+packageName);
		
		//패키지명경로를 파일로로 읽을수 있게 .을 /로 바꾸고,
		String filePath = packageName.replace('.', '/');
		
		//상대경로에서 절대경로를 찾아 dir 에 저장한다
		File dir = Resources.getResourceAsFile(filePath);
		
		System.out.println("filePath = "+filePath);
		System.out.println("dir = "+dir);
		
		//dir : 절대경로
		// 예) C:\Users\bit-user\eclipse-workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\bitcamp-web-09\WEB-INF\classes\bitcamp\pms
		//packageName : 패키지명
		//예) bitcamp.pms
		findClass(dir, packageName);
		
/*		Iterator<String> it = objPool.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
            System.out.println("objPool : "+key);
		}*/
		
		injectDependency();
	}
	
	
	private Object getBean(Class<?> type) {
		Collection<Object> objList = objPool.values();
		for (Object obj : objList) {
			if (type.isInstance(obj)) {
				return obj;
			}
		}
		throw new RuntimeException(type.getName()+"의 객체가 존재하지 않습니다.");
	}
	
	//스텔스로 예외처리?
	public Object getBean(String name) {
		System.out.println("getBean : path = "+name);
		Object obj = objPool.get(name);
		if(obj == null) {
			throw new RuntimeException(name+"의 객체가 존재하지 않습니다.");
		}
		return obj;
	}
	
	public void addBean(String name, Object obj) {
		objPool.put(name, obj);
	}
	
	private void injectDependency() throws Exception {
		//objPool 보관소에 저장된 모든 객체를 꺼낸다.
		Collection <Object> objList = objPool.values();
		
		for (Object obj : objList) {
			// 객체에서 클래스 정보를 추출한다.
			Class<?> clazz = obj.getClass();
			
			//해당 클래스의 모든 메서드를 추출한다.
			Method[] methods = clazz.getMethods();
			
			for (Method m : methods) {
				// 각 객체에 존재하는 메서드 중에서 @Autowired 가 붙은 객체를 찾아낸다.
				if (!m.getName().startsWith("set")) {
					//@Autowired 가 붙은 것 중에서도 set메서드에 붙은것을 찾아야하므로
					continue;
				}
				
				//@Autowired 가 붙지 않았으면 무시
				if(m.getAnnotation(Autowired.class) == null) {
					continue;
				}
				
				//파라미터 개수가 1개가 아니면 무시
				if(m.getParameterTypes().length != 1) {
					continue;
				}
				
				//세터의 파라미터 타입을 알아낸다
				Class<?> paramType = m.getParameterTypes()[0];
				
				//세터의 파라미터 타입에 해당하는 객체를 objPool보관소에서 꺼낸다.
				try {
					Object dependency = getBean(paramType);
					
					//세터를 호출하여 의존객체를 주입한다.
					//invoke(객체명, 객체)를 m -method 에 저장한다.
					//이런걸 Reflection 이라 한다.
					m.invoke(obj, dependency);
				} catch(Exception e) {
					System.out.println("error : "+e.getMessage());
				}
			}
		}
	}

	//inject dependency를 다시 주입하라는거. 객체 주입해서 써야하는데 주입할 객체가 없을 때 다시 주입을 시도하는것
	public void refresh() throws Exception {
		injectDependency();
	}

	void findClass(File path, String  packageName) {
		
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
	
	private void createObject(String className) {
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
	
	private String getObjectName(Class<?> clazz) throws Exception {
		
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

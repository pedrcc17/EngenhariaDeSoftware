package Default;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class SmellyClass {
	private BufferedReader javaFile;
	private String line = "";
	private int method = 0;
	private int linesOfCode = 0;
	private int cyclo_methods = 1;
	private ArrayList<Integer> methodrec = new ArrayList<>();
	private ArrayList<String> methodNames = new ArrayList<>();
	private ArrayList<Integer> linesPerMethod = new ArrayList<>();
	private ArrayList<Boolean> areLongMethods = new ArrayList<>();
	private ArrayList<Integer> cyclosPerMethod = new ArrayList<>();
	private String privado = "private";
	private String pub = "public";
	private boolean isGodClass;
	private int wmcCount;
	private int Loc_Class_var;

	public SmellyClass() {

	}

	public void NOM(BufferedReader file) {
		javaFile = file;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
						&& !line.contains("class") && line.contains("(")){
						
					String esp = line.substring(0,line.indexOf("("));
					String[] pieces =esp.split(" ");
						System.out.println(line);
						String atributes = line.substring(line.indexOf("("), line.indexOf(")")+1);
						String atributes_vf = "";
						
						if(atributes.contains(",")) {
							String[] atributes_v2 = atributes.split(",");
							for(int i=0;i<=atributes_v2.length-1;i++) {
								String[] atributes_v3 = atributes_v2[i].split(" ");
								
								if(atributes_v3.length == 2 ) {
									atributes_vf = atributes_vf + atributes_v3[0]+",";
								}
								else if(atributes_v3.length == 3) {
									atributes_vf = atributes_vf + atributes_v3[1]+",";
								}
							}
							method++;
							atributes_vf=atributes_vf.substring(0, atributes_vf.length() - 1);
							methodName(line,atributes_vf+")");
						}
						else {
							if(atributes.contains(" ")) {
								String[] atributes_v3 = atributes.split(" ");
								atributes_vf = atributes_vf + atributes_v3[0];
								method++;
								methodName(line,atributes_vf+")");
							}
							else {
								String[] atributes_v3 = atributes.split(" ");
								atributes_vf = atributes_vf + atributes_v3[0];
								methodName(line,atributes_vf);
								method++;
							}
							
						}

				
				}
					
				linesOfCode++;
				line = javaFile.readLine();
			}
			javaFile.close();
			System.out.println("The class has " + method + " methods.");
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}
	

	public void WMC(BufferedReader file) {
		javaFile = file;
		cyclo_methods = 0;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
						&& !line.contains("class") && line.contains("(")){
					cyclo_methods++;
				}
				if ((line.contains("while") || line.contains("else") || line.contains("for") || line.contains("if"))
						&& !line.endsWith(";")) {
					cyclo_methods++;
				}
				line = javaFile.readLine();
			}
			javaFile.close();
			System.out.println("The class has a complexity of " + cyclo_methods + ".");
			wmcCount = cyclo_methods;
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}

	public void LOC_Method(BufferedReader file) {
		javaFile = file;
		int linesOfCode = 0;
		int num_method = 0;
		boolean inMethod = false;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
						&& !line.contains("class") && line.contains("(")){
					if (num_method != 0 && inMethod) {
						System.out.println("O " + num_method + "� m�todo tem " + linesOfCode + " linhas de c�digo.");
						inMethod = false;
						linesPerMethod.add(linesOfCode);
					}
					linesOfCode = 0;
					linesOfCode++;
					num_method++;
					inMethod = true;
				} else {
					if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
							&& !line.contains("class") && line.contains("(")){
						if (num_method != 0 && inMethod) {
							System.out
							.println("O " + num_method + "� m�todo tem " + linesOfCode + " linhas de c�digo.");
							inMethod = false;
							linesPerMethod.add(linesOfCode);
						}
						linesOfCode = 0;
						linesOfCode++;
						num_method++;
						inMethod = true;
					} else {
						String[] word = line.split(" ");
						if(word.length != 0) {
							if (inMethod && !line.isEmpty() && !word[0].contains("//") &&  !line.contains("@Override")) {
								linesOfCode++;
							}
						}
					}
				}
				line = javaFile.readLine();
				if (line == null) {
					System.out.println("O " + num_method + "� m�todo tem " + linesOfCode + " linhas de c�digo.");
					linesPerMethod.add(linesOfCode);
				}
			}
			javaFile.close();
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}

	public void CYCLO_Method(BufferedReader file) {
		javaFile = file;
		cyclo_methods = 1;
		int num_method = 0;
		try {
			line = javaFile.readLine();
			while (line != null) {
				if ((line.contains("private") || line.contains("public")) && !line.contains(";") && !line.contains("=") 
						&& !line.contains("class") && line.contains("(")){
					if (num_method != 0) {
						System.out.println("O " + num_method + "� m�todo tem complexidade de " + cyclo_methods + ".");
						cyclosPerMethod.add(cyclo_methods);
					}
					cyclo_methods = 1;
					num_method++;
					line = javaFile.readLine();
				}
				if ((line.contains("while") || line.contains("for") || line.contains("if") || line.contains("else"))
						&& line.endsWith("{")) {
					cyclo_methods++;
				}
				line = javaFile.readLine();
				if (line == null) {
					System.out.println("O " + num_method + "� m�todo tem complexidade de " + cyclo_methods + ".");
					cyclosPerMethod.add(cyclo_methods);
				}
			}
			javaFile.close();
		} catch (IOException e) {
			System.out.println("End of class");
		}
	}

	public boolean isGodClass(int wmcThreshold, int nomThreshold, int locThreshold, boolean wmc, boolean nom, boolean loc,
			boolean isOr, boolean isOrAgain) {
		if (wmc && !nom && !loc) {
			if (wmcCount >= wmcThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (!wmc && nom && !loc) {
			if (method >= nomThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (!wmc && !nom && loc) {
			if (linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if(wmc && nom && !loc && !isOr) {
			if ( wmcCount >= wmcThreshold && method >= nomThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if(wmc && nom && !loc && isOr) {
			if ( wmcCount >= wmcThreshold || method >= nomThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && !nom && loc && !isOr) {
			if (wmcCount >= wmcThreshold && linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && !nom && loc && isOr) {
			if (wmcCount >= wmcThreshold || linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (!wmc && nom && loc && !isOr) {
			if (method >= nomThreshold && linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (!wmc && nom && loc && isOr) {
			if (method >= nomThreshold || linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && nom && loc && !isOr && !isOrAgain) {
			if(wmcCount >= wmcThreshold && method >= nomThreshold && linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && nom && loc && isOr && !isOrAgain) {
			if(wmcCount >= wmcThreshold || method >= nomThreshold || linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && nom && loc && !isOr && isOrAgain) {
			if(wmcCount >= wmcThreshold && method >= nomThreshold || linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		if (wmc && nom && loc && isOr && isOrAgain) {
			if(wmcCount >= wmcThreshold || method >= nomThreshold && linesOfCode >= locThreshold) {
				return true;
			} else {
				return false;
			}
		}
		return false;


	}

	public void isLongMethod(int locTreshold, int cycloTreshold, boolean loc, boolean cyclo, boolean isOr) {
		if (loc && !cyclo) {
			for (int a : linesPerMethod) {
				if (a >= locTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}
			for (int i = 0; i < areLongMethods.size(); i++) {
				System.out.println((i + 1) + "� m�todo -> isLong(" + areLongMethods.get(i) + ")");
			}
		}
		if (!loc && cyclo) {
			for (int a : cyclosPerMethod) {
				if (a >= cycloTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}
			for (int i = 0; i < areLongMethods.size(); i++) {
				System.out.println((i + 1) + "� m�todo -> isLong(" + areLongMethods.get(i) + ")");
			}
		}
		if (loc && cyclo && !isOr) {
			for (int i = 0; i != method; i++) {
				if (linesPerMethod.get(i) >= locTreshold && cyclosPerMethod.get(i) >= cycloTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}
			for (int i = 0; i < areLongMethods.size(); i++) {
				System.out.println((i + 1) + "� m�todo -> isLong(" + areLongMethods.get(i) + ")");
			}
		}
		if (loc && cyclo && isOr) {
			for (int i = 0; i != method; i++) {
				if (linesPerMethod.get(i) >= locTreshold || cyclosPerMethod.get(i) >= cycloTreshold) {
					areLongMethods.add(true);
				} else {
					areLongMethods.add(false);
				}
			}
			for (int i = 0; i < areLongMethods.size(); i++) {
				System.out.println((i + 1) + "� m�todo -> isLong(" + areLongMethods.get(i) + ")");
			}
		}
	}

	public void LOC_Class(BufferedReader file) {
		javaFile = file;
		linesOfCode = (int) javaFile.lines().count();
		System.out.println("A classe tem " + linesOfCode + " linhas de c�digo.");
		linesOfCode++;
	}

	public void methodName(String line, String atributes) {
		String[] parts = line.split("\\(");
		String name = parts[0].substring(parts[0].lastIndexOf(" "));
		System.out.println(name.trim()+atributes);
		methodNames.add(name.trim()+atributes);
	}

	public int getMethod() {
		return method;
	}

	public int getLinesOfCode() {
		return linesOfCode;
	}

	public int getLinesPerMethod(int id) {
		return linesPerMethod.get(id);
	}

	public boolean getAreLongMethods(int id) {
		return areLongMethods.get(id);
	}

	public int getCyclosPerMethod(int id) {
		return cyclosPerMethod.get(id);
	}

	public boolean getisGodClass() {
		return isGodClass;
	}

	public int getWmcCount() {
		return wmcCount;
	}

	public String getMethodNames(int id) {
		return methodNames.get(id);
	}

}
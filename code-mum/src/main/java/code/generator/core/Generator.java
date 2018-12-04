package code.generator.core;

public interface Generator<T extends TableElement> {
	
	String doGenerate(T...elements);
	
}

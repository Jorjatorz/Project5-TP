package tp.pr5.logic;

public interface Observable<T> {

		void addObserver(T o);
		void removeObserver(T o);
	
}

package jmm.example.labaratoriskavtora;

public interface OnContentDownloaded<T> {
	
	public void onContentDownloaded(String content, int httpStatus) throws Exception;
	
	public T getResult();

}

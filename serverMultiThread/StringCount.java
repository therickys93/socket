public class StringCount
{
	private int total;
	
	public StringCount()
	{
		this.total = 0;
		System.out.println("inizializzo totale: "+ this.total);
	}
	
	public synchronized int increment(int value)
	{
		this.total += value;
		return total;
	}
}

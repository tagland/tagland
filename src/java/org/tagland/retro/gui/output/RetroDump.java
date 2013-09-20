package org.tagland.retro.gui.output;

import org.tagland.retro.gui.output.Output;
import org.tagland.retro.gui.output.Strategy;

public class RetroDump {

	public RetroDump(Strategy aStrategy, Output out) {
		this.str = aStrategy;
		this.out = out;
	}
	
	private Strategy str;
	private Output out;
	public String getOutput(){
		return out.toString();
	}
	
	public void print(String s){
		out.print(s);
	}
	
    @SuppressWarnings("rawtypes") 
	public void println(Iterable it){
		out.print(str.lineStartWithIndent());
		for (Object o: it){
			if (o==null){
				out.print(str.placeholder);
			} else {
				out.print(o.toString());
			}
		}
		out.print(str.lineend);
		
	}
    
    public void println(Object o){
    	out.print(str.lineStartWithIndent());
		if (o==null){
			out.print(str.placeholder);
		} else {
			out.print(o.toString());
		}
		out.print(str.lineend);
		out.print(str.newline);
	}
    


	public Strategy getStrategy() {
		return str;
	}
	
	
	

}